package com.thardal.bankapp.customer.controller;

import com.thardal.bankapp.customer.dto.CustomerDto;
import com.thardal.bankapp.customer.dto.CustomerSaveRequestDto;
import com.thardal.bankapp.customer.dto.CustomerUpdateRequestDto;
import com.thardal.bankapp.customer.service.CustomerService;
import com.thardal.bankapp.global.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(tags = "Customer Controller", summary = "Find all customers")
    @GetMapping
    public ResponseEntity findAll() {
        List<CustomerDto> customerDtos = customerService.findAll();

        return ResponseEntity.ok(RestResponse.of(customerDtos));
    }

    @Operation(tags = "Customer Controller", summary = "Find customer by id")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        CustomerDto customerDto = customerService.findById(id);
        return ResponseEntity.ok(RestResponse.of(customerDto));
    }

    @Operation(tags = "Customer Controller", summary = "Add customer")
    @PostMapping
    public ResponseEntity save(@RequestBody CustomerSaveRequestDto customerSaveRequestDto) {
        CustomerDto customerDto = customerService.save(customerSaveRequestDto);

        // WebMvcLinkBuilder is used to create links to methods
        WebMvcLinkBuilder linkGet = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).findById(customerDto.getId()));

        WebMvcLinkBuilder linkDelete = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).delete(customerDto.getId()));

        // entityModel is used to add links to the response
        EntityModel entityModel = EntityModel.of(customerDto);

        // Add the link to the response
        entityModel.add(linkGet.withRel("find-by-id"));
        entityModel.add(linkDelete.withRel("delete"));

        // serialize the response
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(entityModel);

        return ResponseEntity.ok(RestResponse.of(mappingJacksonValue));
    }

    @Operation(tags = "Customer Controller", summary = "Delete customer by id")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        customerService.delete(id);

        return ResponseEntity.ok(RestResponse.empty());
    }

    @Operation(tags = "Customer Controller", summary = "Update customer")
    @PutMapping
    public ResponseEntity update(@RequestBody CustomerUpdateRequestDto customerUpdateRequestDto) {
        CustomerDto update = customerService.update(customerUpdateRequestDto);
        return ResponseEntity.ok(RestResponse.of(update));
    }
}
