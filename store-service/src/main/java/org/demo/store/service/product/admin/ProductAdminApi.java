package org.demo.store.service.product.admin;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demo.store.common.product.ProductData;
import org.demo.store.service.product.exception.ProductAlreadyCreatedException;
import org.demo.store.service.product.exception.ProductNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductAdminApi {

  private final ProductAdminService adminService;

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  @Operation(summary = "Retrieve a list of all products")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "List of products was retrieved"),
          @ApiResponse(responseCode = "500", description = "Unexpected error")
  })
  public List<ProductData> list() {
    return adminService.getAll();
  }

  @ResponseStatus(CREATED)
  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @Operation(summary = "Create a new product")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Product was created"),
          @ApiResponse(responseCode = "400", description = "Request is not valid"),
          @ApiResponse(responseCode = "500", description = "Unexpected error")
  })
  public ProductData create(@RequestBody @Valid ProductData data) {
    return adminService.create(data);
  }

  @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @Operation(summary = "Update an existing product and returns updated value")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Product was updated"),
          @ApiResponse(responseCode = "400", description = "Request is not valid"),
          @ApiResponse(responseCode = "404", description = "Product not found"),
          @ApiResponse(responseCode = "500", description = "Unexpected error")
  })
  public ProductData update(@RequestBody @Valid ProductData data) {
    return adminService.update(data);
  }

  @DeleteMapping
  @Operation(summary = "Disable an existing product")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Product was created"),
          @ApiResponse(responseCode = "400", description = "Request is not valid"),
          @ApiResponse(responseCode = "404", description = "Product not found"),
          @ApiResponse(responseCode = "500", description = "Unexpected error")
  })
  public void disable(@Parameter(description = "Sku id of an existing product") @RequestParam long skuId) {
    adminService.disable(skuId);
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler({MethodArgumentNotValidException.class, ProductAlreadyCreatedException.class})
  public void handleValidationError(Exception e) {
    log.error("400 error in product api", e);
  }

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(ProductNotFoundException.class)
  public void handleProductNotFound(Exception e) {
    log.error("404 error in product api", e);
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public void handleException(Exception e) {
    log.error("500 error in product api", e);
  }
}
