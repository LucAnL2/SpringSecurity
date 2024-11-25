package vn.iotstar.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.iotstar.Service.ProductService;
import vn.iotstar.Entity.Product;



@Controller
public class LoginController {
	@Autowired
	private ProductService service;
	
	@PostMapping("/login_success_handler")
	public String loginSuccessHandler() {
		System.out.println("Logging user login success...");
		return "index";
	}
	
	@PostMapping("/login_failure_handler")
	public String loginFailureHandler() {
		System.out.println("login failure handler...");
		
		return "login";
	}
	
	@RequestMapping("/")
	public String viewHomePage(Model model)
	{
		List<Product> listProducts = service.listAll();
		model.addAttribute("listProducts",listProducts);
		
		return "index";
	}
	@RequestMapping("/new")
	public String showNewProducForm(Model model, @ModelAttribute("product") Product product) {
		model.addAttribute("product", product);
		return "new_product";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {
		service.save(product);
		return "redirect:/";
	}
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductForm(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_product");
		Product product = service.get(id);
		mav.addObject("product",product);
		
		return mav;
	}
	@RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        service.delete(id);

        return "redirect:/";

    }
}
