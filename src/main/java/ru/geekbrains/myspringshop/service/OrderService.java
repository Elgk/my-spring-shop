package ru.geekbrains.myspringshop.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.geekbrains.myspringshop.entity.Order;
import ru.geekbrains.myspringshop.entity.repository.OrderRepository;
import ru.geekbrains.myspringshop.util.PersonUtil;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }


    public List<Order> findOrdersByPerson(){
        return  orderRepository.findOrdersByPerson(PersonUtil.getCurrentPerson());
    }

    public Order save(Order order) {
        var savedOrder = orderRepository.save(order);
        try {
            var subject = String.format("Заказ с номером %s успешно создан", savedOrder.getId());
            var messageText = String.format("Заказ с номером %s успешно создан. Заказ будет доставлен в течении суток " +
                    "на адрес %s", savedOrder.getId(), savedOrder.getAddress());

            if(PersonUtil.getCurrentPerson() == null || StringUtils.isBlank(PersonUtil.getCurrentPerson().getEmail())) {
                System.out.println("Почта не заполнена");
                return savedOrder;
            }

            MailService.sendMessage(PersonUtil.getCurrentPerson().getEmail(), subject, messageText);
        } catch (GeneralSecurityException | IOException | MessagingException e) {
            System.out.println("Не удалось отправить письмо");
            e.printStackTrace();
        }

        return savedOrder;
    }
}
