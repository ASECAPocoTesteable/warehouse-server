package service

import model.Order
import model.OrderProduct
import repository.OrderRepository
import repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import repository.WarehouseRepository
import util.OrderDTO
import java.util.UUID

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val warehouseRepository: WarehouseRepository,
    private val stockService: StockService
) {

    @Transactional
    fun createOrder(orderDTO: OrderDTO): Order {
        val warehouse = findWarehouseById(orderDTO.warehouseId)
        val order = Order(
            status = orderDTO.status, warehouse = warehouse, orderProducts = mutableListOf()
        )
        val products = mutableListOf<OrderProduct>()
        for (orderProductDTO in orderDTO.orderProducts) {
            val product = productRepository.findById(orderProductDTO.productId) ?: throw NoSuchElementException("Product not found")
            if ((product.stock?.quantity ?: 0) < orderProductDTO.quantity) {
                throw IllegalArgumentException("Not enough stock for product: ${product.name}")
            }
            product.stock?.quantity = product.stock?.quantity?.minus(orderProductDTO.quantity) ?: 0
            productRepository.save(product)
            products.add(OrderProduct(order = order, product = product, quantity = orderProductDTO.quantity))
        }
        order.orderProducts = products
        return orderRepository.save(order)
    }

    @Transactional
    fun updateOrder(orderDTO: OrderDTO): Order { //Testear
        val order = orderRepository.findById(orderDTO.id)
            .orElseThrow { NoSuchElementException("Order not found") }
        order.status = orderDTO.status
        order.orderProducts = orderDTO.orderProducts.map {
            OrderProduct(
                order = order,
                product = productRepository.findById(it.productId) ?: throw NoSuchElementException("Product not found"),
                quantity = it.quantity
            )
        }
        return orderRepository.save(order)
    }

    @Transactional
    fun deleteOrder(id: UUID) = orderRepository.deleteById(id)

    @Transactional(readOnly = true)
    fun getOrderById(id: UUID): Order = orderRepository.findById(id).orElseThrow { NoSuchElementException("Order not found") }
    fun getOrderStatus(id: UUID): String? {
        val order = orderRepository.findById(id).orElseThrow { NoSuchElementException("Order not found") }
        return order.status.name
    }
 @Transactional
fun checkStockAndCreateOrder(orderDTO: OrderDTO): Order {
    val warehouse = findWarehouseById(orderDTO.warehouseId)
    val order = Order(
        status = orderDTO.status, warehouse = warehouse, orderProducts = mutableListOf()
    )
    val products = mutableListOf<OrderProduct>()
    for (orderProductDTO in orderDTO.orderProducts) {
        val product = productRepository.findById(orderProductDTO.productId) ?: throw NoSuchElementException("Product not found")
        if ((product.stock?.quantity ?: 0) < orderProductDTO.quantity) {
            throw IllegalArgumentException("Not enough stock for product: ${product.name}")
        }
        product.stock?.quantity = product.stock?.quantity?.minus(orderProductDTO.quantity) ?: 0
        productRepository.save(product)
        products.add(OrderProduct(order = order, product = product, quantity = orderProductDTO.quantity))
    }
    order.orderProducts = products
    return orderRepository.save(order)
}
    private fun findWarehouseById(id: UUID) = warehouseRepository.findById(id).orElseThrow { NoSuchElementException("Warehouse not found") }
}
