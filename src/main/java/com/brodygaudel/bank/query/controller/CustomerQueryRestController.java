package com.brodygaudel.bank.query.controller;

import com.brodygaudel.bank.query.dto.CustomerResponseDTO;
import com.brodygaudel.bank.query.model.GetAllCustomersQuery;
import com.brodygaudel.bank.query.model.GetCustomerByIdQuery;
import com.brodygaudel.bank.query.model.SearchCustomersQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queries/customers")
public class CustomerQueryRestController {

    private final QueryGateway queryGateway;


    public CustomerQueryRestController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/get/{id}")
    public CustomerResponseDTO getById(@PathVariable String id){
        return queryGateway.query(new GetCustomerByIdQuery(id),
                ResponseTypes.instanceOf(CustomerResponseDTO.class)
        ).join();
    }

    @GetMapping("/search")
    public List<CustomerResponseDTO> search(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "100") int size){

        return queryGateway.query(new SearchCustomersQuery(keyword, page, size),
                ResponseTypes.multipleInstancesOf(CustomerResponseDTO.class)
        ).join();
    }

    @GetMapping("/list")
    public List<CustomerResponseDTO> getAll(){
        return queryGateway.query( new GetAllCustomersQuery(),
                ResponseTypes.multipleInstancesOf(CustomerResponseDTO.class)
        ).join();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
