package com.example.test.mapper;

import com.example.test.dto.request.OrderRequest;
import com.example.test.dto.response.OrderDetailResponse;
import com.example.test.dto.response.OrderResponse;
import com.example.test.entity.Order;
import com.example.test.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderRequest request);

    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "orderStatus", source = "orderStatus.name")
    @Mapping(target = "orderDetailResponses", expression = "java(buildOrderDetails(order))")
    OrderResponse toOrderResponse(Order order);

    default List<OrderDetailResponse> buildOrderDetails(Order order) {
        List<OrderDetail> orderDetails = order.getOrderDetails();

        return orderDetails.stream().map(this::toOrderDetailResponse).collect(Collectors.toList());
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "unitPrice", source = "unitPrice")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "totalPrice", expression = "java(orderDetail.getUnitPrice() * orderDetail.getQuantity())")
    @Mapping(target = "product", source = "product.name")
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}
