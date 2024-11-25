package vn.iotstar.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.iotstar.Entity.Product;


@Service
public interface ProductService {
	void delete(Long id);

	Product get(Long id);

	Product save(Product product);

	List<Product> listAll();
}
