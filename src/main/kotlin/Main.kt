val prod1 = Product("Prod1",1.0)
val prod2 = Product("Prod2",2.0)
val prod3 = Product("Prod3",3.0)
val prod4 = Product("Prod4",4.0)
//1
data class Shop(val name: String, val customers: List<Customer>)
data class City(val name: String)
data class Customer(val name: String, val city: City, val orders: List<Order>)
data class Order(val products: List<Product>, val isDelivered: Boolean)
data class Product(val name: String, val price: Double)

fun Shop.getSetOfCustomers(): Set<Customer> = this.customers.toSet()
//2
// Return the set of cities the customers are from
fun Shop.getCitiesCustomersAreFrom(): Set<String> = customers.map { it.city.name }.toSet()

// Return a list of the customers who live in the given city
fun Shop.getCustomersFrom(city: City): List<Customer> = customers.filter { it.city==city }

//3
// Return true if all customers are from the given city
fun Shop.checkAllCustomersAreFrom(city: City): Boolean = customers.all { it.city == city }

// Return true if there is at least one customer from the given city
fun Shop.hasCustomerFrom(city: City): Boolean = customers.any { it.city == city }

// Return the number of customers from the given city
fun Shop.countCustomersFrom(city: City): Int = customers.count { it.city == city }

// Return a customer who lives in the given city, or null if there is none
fun Shop.findAnyCustomerFrom(city: City): Customer? = customers.find { it.city == city }

//4
// Return all products this customer has ordered
fun Customer.getOrderedProducts(): Set<Product> = orders.flatMap { it.products }.toSet()

// Return all products that were ordered by at least one customer
fun Shop.getAllOrderedProducts(): Set<Product> = customers.flatMap { it.getOrderedProducts() }.toSet()

//5
// Return a customer whose order count is the highest among all customers
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? =customers.maxByOrNull { it.orders.size }

// Return the most expensive product which has been ordered
fun Customer.getMostExpensiveOrderedProduct(): Product? = orders.flatMap(Order::products).maxByOrNull(Product::price)

//6
fun Shop.getCustomersSortedByNumberOfOrders(): List<Customer> = customers.sortedBy { it.orders.size }
//7
fun Customer.getTotalOrderPrice(): Double = orders.sumOf {it.products.sumOf { it.price }  }
//8
fun Shop.groupCustomersByCity(): Map<City, List<Customer>> = customers.groupBy {it.city }
//9
fun Shop.getCustomersWithMoreUndeliveredOrdersThanDelivered(): Set<Customer> = customers.filter {
    val (delivered, undelivered) = it.orders.partition { it.isDelivered }
    undelivered.size > delivered.size
}.toSet()
//10
fun Shop.getSetOfProductsOrderedByEveryCustomer(): Set<Product> {
    return customers.fold(customers.flatMap { it.getOrderedProducts() }.toSet()){products, customer ->products.intersect(customer.getOrderedProducts())}
}

//11
// Return the most expensive product among all delivered products
// (use the Order.isDelivered flag)
fun Customer.getMostExpensiveDeliveredProduct(): Product? {
    return this.orders.filter { order -> order.isDelivered }.flatMap { it.products }.maxByOrNull { it.price }
}

// Return how many times the given product was ordered.
// Note: a customer may order the same product for several times.
fun Shop.getNumberOfTimesProductWasOrdered(product: Product): Int {
    return this.customers.flatMap { it.orders.flatMap { it.products } }.count { it == product }
}
//12
fun doSomethingStrangeWithCollection(
    collection: Collection<String>
): MutableCollection<String?>? {
    val groupsByLength: MutableMap<Int, MutableList<String?>?> = HashMap()
    for (s in collection) {
        var strings = groupsByLength[s.length]
        if (strings == null) {
            strings = ArrayList()
            groupsByLength[s.length] = strings
        }
        strings.add(s)
    }
    var maximumSizeOfGroup = 0
    for (group in groupsByLength.values) {
        if (group!!.size > maximumSizeOfGroup) {
            maximumSizeOfGroup = group.size
        }
    }
    for (group in groupsByLength.values) {
        if (group!!.size == maximumSizeOfGroup) {
            return group
        }
    }
    return null
}

fun main(args: Array<String>) {
    println("#1")
    val customers:List<Customer> =listOf(Customer("test1",City("Ufa"), listOf(Order(listOf(prod1,prod2),false),Order(listOf(prod3,prod2),false))),Customer("test",City("Ufa"), listOf(Order(listOf(prod2,prod3),true),Order(listOf(prod3,prod2),false))), Customer("chuck",City("Moscow"), listOf(Order(listOf(prod2,prod4),true),Order(listOf(prod3,prod4),false))), Customer("gek",City("Detroit"), listOf(Order(listOf(prod4,prod1),true),Order(listOf(prod1,prod2),false))))
    val shop = Shop("Sample", customers)
    println(shop.getSetOfCustomers() is Set)
    println("#2")
    println(shop.getCitiesCustomersAreFrom())
    println(shop.getCustomersFrom(City("Ufa")))
    println("#3")
    println(shop.checkAllCustomersAreFrom(City("Ufa")))
    println(shop.hasCustomerFrom(City("Ufa")))
    println(shop.countCustomersFrom(City("Ufa")))
    println(shop.findAnyCustomerFrom(City("Ufa")))
    println("#4")
    println(customers[0].getOrderedProducts())
    println(shop.getAllOrderedProducts())
    println("#5")
    println(shop.getCustomerWithMaximumNumberOfOrders())
    println(customers[0].getMostExpensiveOrderedProduct())
    println("#6")
    println(shop.getCustomersSortedByNumberOfOrders())
    println("#7")
    println(customers[0].getTotalOrderPrice())
    println("#8")
    println(shop.groupCustomersByCity())
    println("#9")
    println(shop.getCustomersWithMoreUndeliveredOrdersThanDelivered())
    println("#10")
    println(shop.getSetOfProductsOrderedByEveryCustomer())
    println("#11")
    println(customers[0].getMostExpensiveOrderedProduct())
    println(shop.getNumberOfTimesProductWasOrdered(prod2))
    println("#12")
    println(doSomethingStrangeWithCollection(listOf("one","two","three","four","five","six","seven","eight","nine","ten","1234","2345","3456")))

}