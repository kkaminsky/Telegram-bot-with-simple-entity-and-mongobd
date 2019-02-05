package com.example.lab06.service

import com.example.lab06.entity.Contact
import com.example.lab06.entity.Customer
import com.example.lab06.entity.Order

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.CallbackQuery
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.exceptions.TelegramApiException
import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executor


@Service
class TelegramBotImpl @Autowired constructor(
        private val executor: Executor,
        private val telegramUserService: TelegramUserService,
        private val contactService: ContactService,
        private val customerService: CustomerService,
        private val serviseServise:ServiceService,
        private val employeeService: EmployeeService,
        private val orderService: OrderService
): TelegramLongPollingBot(), TelegramBot  {

    private val logger = LoggerFactory.getLogger(TelegramBotImpl::class.java)

    override fun getBotUsername(): String {
        return "YOUR_BOT"
    }

    override fun getBotToken(): String {
        return "YOUR_TOKEN"
    }

    override fun onUpdateReceived(update: Update) {
        executor.execute {
            parseComand(update)


        }
    }

    private fun parseComand(update: Update){
        val status = checkStatus(update)
        when(status){
            0->{
                if (update.hasCallbackQuery()) {
                    updateStatusForTable(update.callbackQuery,update.callbackQuery.data)
                } else if (update.message != null)
                    sendFirstMsg(update.getMessage())
                    sendAddContact(update.message)


            }

            1-> {
                if (update.hasCallbackQuery()){
                    updateStatusForAction(update.callbackQuery,update.callbackQuery.data)
                }
                else if (update.message != null){
                    sendSecondMsg(update.getMessage())
                }
            }
            2-> {
                if (update.message != null){
                    updateStatusForFinish(update.getMessage())
                    sendFirstMsg(update.getMessage())
                }
            }
        }

    }

    private fun updateStatusForFinish(message: Message){
        if (telegramUserService.getTable(message.from.id)=="Contact"){
            if(telegramUserService.getAction(message.from.id)=="Добавить") {
                val text = message.text.split(' ')
                var contact = Contact()
                contact.number = text[0]
                contact.email = text[1]
                contactService.add(contact)
            }
            else if(telegramUserService.getAction(message.from.id)=="Удалить") {
                contactService.remove(message.text)
            }
            else if (telegramUserService.getAction(message.from.id)=="Изменить"){
                val text = message.text.split(' ')
                var contact = contactService.get(text[0])
                contact?.number = text[1]
                contact?.email = text[2]
                contactService.update(contact!!)
            }
            else if (telegramUserService.getAction(message.from.id)=="Поиск"){
                val contact = contactService.get(message.text)
                if (contact!=null){
                    sendResultOfSearch(message,contact)
                }
            }
        }
        else if (telegramUserService.getTable(message.from.id)=="Customer"){
            if(telegramUserService.getAction(message.from.id)=="Добавить") {
                val text = message.text.split(' ')
                var customer = Customer()
                customer.name = text[0]
                customer.contact = contactService.get(text[1])
                customerService.add(customer)
            }
            else if(telegramUserService.getAction(message.from.id)=="Удалить") {
                customerService.remove(message.text)
            }
            else if (telegramUserService.getAction(message.from.id)=="Изменить"){
                val text = message.text.split(' ')
                var customer = customerService.get(text[0])
                customer?.name = text[1]
                customer?.contact = contactService.get(text[2])
                customerService.update(customer!!)
            }
            else if (telegramUserService.getAction(message.from.id)=="Поиск"){
                val customer = customerService.get(message.text)
                if (customer!=null){
                    sendResultOfSearch(message,customer)
                }
            }
        }
        else if (telegramUserService.getTable(message.from.id)=="Service"){
            if(telegramUserService.getAction(message.from.id)=="Добавить") {
                val text = message.text.split(' ')
                var service = com.example.lab06.entity.Service()
                service.name = text[0]
                service.description = text[1]
                service.cost = text[2].toDouble()
                serviseServise.add(service)
            }
            else if(telegramUserService.getAction(message.from.id)=="Удалить") {
                serviseServise.remove(message.text)
            }
            else if (telegramUserService.getAction(message.from.id)=="Изменить"){
                val text = message.text.split(' ')
                var service = serviseServise.get(text[0])
                service!!.name = text[1]
                service!!.description = text[2]
                service!!.cost = text[3].toDouble()
                serviseServise.update(service)
            }
            else if (telegramUserService.getAction(message.from.id)=="Поиск"){
                val service = serviseServise.get(message.text)
                if (service!=null){
                    sendResultOfSearch(message,service)
                }
            }
        }
        else if (telegramUserService.getTable(message.from.id)=="Order"){
            if(telegramUserService.getAction(message.from.id)=="Добавить") {
                val text = message.text.split(' ')
                var order = Order()
                order.startDate= LocalDate.parse(text[0])
                order.finishDate = LocalDate.parse(text[1])
                order.orderDate = LocalDate.parse(text[2])
                order.cost=text[3].toDouble()
                order.location=text[4]
                order.bit = text[5].equals("+")
                order.customer = customerService.get(text[6])
                order.employee = employeeService.get(text[7])
                order.services = getListService(text[8].split(','))
                orderService.add(order)

            }
            else if(telegramUserService.getAction(message.from.id)=="Удалить") {
                orderService.remove(message.text)
            }
            else if (telegramUserService.getAction(message.from.id)=="Изменить"){
                val text = message.text.split(' ')
                var order = orderService.get(text[0])
                order!!.startDate= LocalDate.parse(text[1])
                order!!.finishDate = LocalDate.parse(text[2])
                order!!.orderDate = LocalDate.parse(text[3])
                order!!.cost=text[4].toDouble()
                order!!.location=text[5]
                order!!.bit = text[6].equals("+")
                order!!.customer = customerService.get(text[7])
                order!!.employee = employeeService.get(text[8])
                order!!.services = getListService(text[9].split(','))
                orderService.update(order)

            }
            else if (telegramUserService.getAction(message.from.id)=="Поиск"){
                val order = orderService.get(message.text)
                if (order!=null){
                    sendResultOfSearch(message,order)
                }
            }
        }

        telegramUserService.resetStatus(message.from.id)


    }

    private fun getListService(strings:List<String>):List<com.example.lab06.entity.Service>{
        var a = mutableListOf<com.example.lab06.entity.Service>()
        for(i in strings){
            a.add(serviseServise.get(i)!!)
        }
        return a
    }
    private fun sendResultOfSearch(message: Message,order: Order){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = order.toString()
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }
    private fun sendResultOfSearch(message: Message,service:com.example.lab06.entity.Service){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = service.toString()
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendResultOfSearch(message: Message,customer: Customer){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = customer.toString()
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendResultOfSearch(message: Message,contact: Contact){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = contact.id + " " + contact.number + " " +contact.email
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun checkStatus(update: Update):Int{
        val userTelegramId: Int

        if (update.getMessage() != null)
            userTelegramId = update.message.from.id
        else
            userTelegramId = update.callbackQuery.from.id
        return telegramUserService.getStatus(userTelegramId)
    }


    private fun updateStatusForAction(callback: CallbackQuery, action:String){

        telegramUserService.updateAction(callback.from.id,action)
        if (telegramUserService.getTable(callback.from.id)=="Contact")
            if(action=="Показать все"){
                sendGetAllContact(callback.message)
                telegramUserService.resetStatus(callback.from.id)
                sendFirstMsg(callback.message)
            }
            else if (action=="Добавить")
                sendAddContact(callback.message)
            else if (action == "Изменить")
                sendChageContact(callback.message)
            else if (action=="Удалить")
                sendDeleteContact(callback.message)
            else if (action=="Поиск"){
                sendSearchContact(callback.message)
            }
        if (telegramUserService.getTable(callback.from.id)=="Customer"){
            if(action=="Показать все"){
                sendGetAllCustomer(callback.message)
                telegramUserService.resetStatus(callback.from.id)
                sendFirstMsg(callback.message)
            }
            else if (action=="Добавить")
                sendAddCustomer(callback.message)
            else if (action == "Изменить")
                sendChageCustomer(callback.message)
            else if (action=="Удалить")
                sendDeleteContact(callback.message)
            else if (action=="Поиск"){
                sendSearchContact(callback.message)
            }
        }
        if (telegramUserService.getTable(callback.from.id)=="Order"){
            if(action=="Показать все"){
                sendAllOrder(callback.message)
                telegramUserService.resetStatus(callback.from.id)
                sendFirstMsg(callback.message)
            }
            else if (action=="Добавить")
                sendAddOrder(callback.message)
            else if (action == "Изменить")
                sendChangeOrder(callback.message)
            else if (action=="Удалить")
                sendDeleteContact(callback.message)
            else if (action=="Поиск"){
                sendSearchContact(callback.message)
            }
        }
        if (telegramUserService.getTable(callback.from.id)=="Service"){
            if(action=="Показать все"){
                sendAllService(callback.message)
                telegramUserService.resetStatus(callback.from.id)
                sendFirstMsg(callback.message)
            }
            else if (action=="Добавить")
                sendAddService(callback.message)
            else if (action == "Изменить")
                sendChangeService(callback.message)
            else if (action=="Удалить")
                sendDeleteContact(callback.message)
            else if (action=="Поиск"){
                sendSearchContact(callback.message)
            }
        }

    }

    private fun sendAddService(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = "Введите через пробел имя, описание, цену"
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendChangeService(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = "Введите через пробел id услуги, новое имя, описание цену"
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendAllService(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        val service = serviseServise.getAll()
        var text = ""
        for(i in service) {
            text = text + i.toString() + "\n"
        }
        sm.text=text
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendChangeOrder(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = "Введите через пробел id заказа,начальную дату, конечную дату, дату заказа, цену, расположение, отметку об оплате(+ или -), id заказчика, id сотрудника, id услуг через запятую."
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendAddOrder(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = "Введите через пробел начальную дату, конечную дату, дату заказа, цену, расположение, отметку об оплате(+ или -), id заказчика, id сотрудника, id услуг через запятую."
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendAllOrder(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        val orders = orderService.getAll()
        var text = ""

        for(i in orders) {
            text = text + i.toString() + "\n"
        }
        sm.text=text
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendChageCustomer(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = "Введите id, новое имя и id новых контактных данных через пробел"
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }

    }

    private fun sendAddCustomer(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = "Введите имя и id контактных данных через пробел "
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendGetAllCustomer(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        val customers = customerService.getAll()
        var text = ""
        for(i in customers) {
            text = text + i.toString() + "\n"
        }
        sm.text=text
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendSearchContact(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = "Введите id"
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendDeleteContact(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = "Введите id"
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendChageContact(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = "Введите id и новый номер и e-mail через пробел"
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendAddContact(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        sm.text = "Введите номер и e-mail через пробел "
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }

    private fun sendGetAllContact(message: Message){
        val sm = SendMessage()
        sm.chatId = message.chatId.toString()
        val contacts = contactService.getAll()
        var text = ""
        for(i in contacts) {
            text = text + i.toString() + "\n"
        }
        sm.text=text
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }

    }
    private fun updateStatusForTable(callback: CallbackQuery, table:String) {

        telegramUserService.updateTable(callback.from.id,table)
        sendSecondMsg(callback.message)

    }

    private fun sendSecondMsg(message: Message){
        val sm = SendMessage()
        sm.enableMarkdown(true)
        sm.chatId = message.chatId.toString()
        sm.text = "Выбирите действие: "
        val markupInline = InlineKeyboardMarkup()
        val rowsInline = ArrayList<List<InlineKeyboardButton>>()
        var rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("Добавить").setCallbackData("Добавить"))
        rowsInline.add(rowInline)

        rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("Изменить").setCallbackData("Изменить"))
        rowsInline.add(rowInline)

        rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("Удалить").setCallbackData("Удалить"))
        rowsInline.add(rowInline)

        rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("Поиск").setCallbackData("Поиск"))
        rowsInline.add(rowInline)

        rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("Показать все").setCallbackData("Показать все"))
        rowsInline.add(rowInline)


        markupInline.keyboard = rowsInline
        sm.replyMarkup = markupInline
        try {
            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }




    private fun sendFirstMsg(message: Message){
        val sm = SendMessage()
        sm.enableMarkdown(true)
        sm.chatId = message.chatId.toString()
        sm.text = "Выбирите таблицу: "
        val markupInline = InlineKeyboardMarkup()
        val rowsInline = ArrayList<List<InlineKeyboardButton>>()
        var rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("Contact").setCallbackData("Contact"))
        rowsInline.add(rowInline)

        rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("Employee").setCallbackData("Employee"))
        rowsInline.add(rowInline)

        rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("Customer").setCallbackData("Customer"))
        rowsInline.add(rowInline)

        rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("Order").setCallbackData("Order"))
        rowsInline.add(rowInline)

        rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("Position").setCallbackData("Position"))
        rowsInline.add(rowInline)

        rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("Service").setCallbackData("Service"))
        rowsInline.add(rowInline)

        rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("TypeOfAdvertising").setCallbackData("TypeOfAdvertising"))
        rowsInline.add(rowInline)

        rowInline = ArrayList<InlineKeyboardButton>()
        rowInline.add(InlineKeyboardButton().setText("AdvertisingInstalation").setCallbackData("AdvertisingInstalation"))
        rowsInline.add(rowInline)



        markupInline.keyboard = rowsInline
        sm.replyMarkup = markupInline
        try {

            execute(sm)
        } catch (e: TelegramApiException) {
            logger.error(e.message, e)
        }
    }


}