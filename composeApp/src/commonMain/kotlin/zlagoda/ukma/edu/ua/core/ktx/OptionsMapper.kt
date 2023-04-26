package zlagoda.ukma.edu.ua.core.ktx

import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Cheque
import zlagoda.ukma.edu.ua.db.CustomerCard
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.db.Sale
import zlagoda.ukma.edu.ua.db.StoreProduct

internal fun buildAll(
    products: List<Product>,
    categories: List<Category>,
    employees: List<Employee>,
    customerCards: List<CustomerCard>,
    cheques: List<Cheque>,
    sales: List<Sale>,
    storeProducts: List<StoreProduct>
): String {
    return  "Products\n" + products.toProductsStr() +
        "\n\nCategories\n" + categories.toCategoryStr() +
        "\n\nEmployees\n" + employees.toEmployeeStr() +
        "\n\nCustomerCards\n" + customerCards.toCustomerCardStr() +
        "\n\nCheques\n" + cheques.toChequeStr() +
        "\n\nSales\n" + sales.toSalesStr() +
        "\n\nStoreProducts\n" + storeProducts.toStoreProductStr()
}

internal fun List<Category>.toCategoryStr(): String {
    return "id,name\n" + this.joinToString(separator = "\n") { it.toStr() }
}

internal fun Category.toStr(): String = "$id,$name"


internal fun List<Product>.toProductsStr(): String {
    return "id,category name,product name,characteristics\n" + this.joinToString(separator = "\n") { it.toStr() }
}

internal fun Product.toStr(): String {
    return "$idProduct,$categoryNumber,$productName,$characteristics"
}

internal fun List<Employee>.toEmployeeStr(): String {
    return "employee id,employee surname,employee name,employee patronymic,employee role,salary,date of birth,date of start,phone number,city,street,zip code\n" + this.joinToString(separator = "\n") { it.toStr() }
}

internal fun Employee.toStr(): String {
    return "$id_of_employee,$empl_surname,$empl_name,$empl_patronymic,$empl_role,$salary,$date_of_birth,$date_of_start,$phone_number,$city,$street,$zip_code"
}

internal fun List<CustomerCard>.toCustomerCardStr(): String {
    return "card number,customer surname,customer name,customer patronymic,phone number,city,street,zip code,percent\n" + this.joinToString(separator = "\n") { it.toStr() }
}

internal fun CustomerCard.toStr(): String {
    return "$cardNumber,$custSurname,$custName,$custPatronymic,$phoneNumber,$city,$street,$zipCode,$percent"
}


internal fun List<Cheque>.toChequeStr(): String {
    return "cheque number,employee id,card number,print date,sum total,vat\n" + this.joinToString(separator = "\n") { it.toStr() }
}

internal fun Cheque.toStr(): String {
    return "$chequeNumber,$idEmployee,$cardNumber,$printDate,$sumTotal,$vat"
}

internal fun List<Sale>.toSalesStr(): String {
    return "upc,cheque number,product number,sell price" + this.joinToString(separator = "\n") { it.toStr() }
}

internal fun Sale.toStr(): String {
    return "$upc,$chequeNumber,$productNumber,$sellingPrice"
}

internal fun List<StoreProduct>.toStoreProductStr(): String {
    return "upc,upcProm,product id,sell price,count products,promote product\n" + this.joinToString(separator = "\n") { it.toStr() }
}

internal fun StoreProduct.toStr(): String {
    return "$upc,$upcProm,$idProduct,$sellingPrice,$productsNumber,$promotionalProduct"
}