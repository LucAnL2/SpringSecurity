package vn.iotstar.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vn.iotstar.models.Customer;

@RestController
@EnableMethodSecurity
public class CustomerController {

	
	// Danh sách khách hàng mẫu
	private final List<Customer> customers = List.of(
			Customer.builder().id("001").name("Nguyễn Hữu Trung").email("trungnhspkt@gmail.com").build(),
			Customer.builder().id("002").name("Hữu Trung").email("trunghuu@gmail.com").build());

	// Endpoint chào
	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("Hello, Guest");
	}

	// Endpoint lấy toàn bộ danh sách khách hàng (yêu cầu quyền ADMIN)
	@GetMapping("/customer/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Customer>> getCustomerList() {
		return ResponseEntity.ok(customers);
	}

	// Endpoint lấy thông tin khách hàng theo ID (yêu cầu quyền USER)
	@GetMapping("/customer/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") String id) {
		return customers.stream().filter(customer -> customer.getId().equals(id)).findFirst().map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
}
