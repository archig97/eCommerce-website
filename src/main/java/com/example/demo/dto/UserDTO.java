package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Cart;
import com.example.demo.model.Order;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDTO> order;
    //sending back OrderDTO to avoid another cyclic dependency
    //will need a CartDTO as well
    private CartDTO cart;

}
