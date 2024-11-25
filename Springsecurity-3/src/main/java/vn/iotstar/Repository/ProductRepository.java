package vn.iotstar.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
