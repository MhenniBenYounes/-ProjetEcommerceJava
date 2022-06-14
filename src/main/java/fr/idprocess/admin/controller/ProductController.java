package fr.idprocess.admin.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import fr.idprocess.common.model.Category;
import fr.idprocess.common.model.Marque;
import fr.idprocess.common.model.Product;
import fr.idprocess.common.service.CategoryService;
import fr.idprocess.common.service.MarqueService;
import fr.idprocess.common.service.ProductService;

@Controller
@RequestMapping("/admin/produits")
public class ProductController {
	
	@Autowired
	ProductService produitService;
	
	@Autowired
	CategoryService categorieService;
	
	@Autowired
	MarqueService marqueService;

	@GetMapping("")
	public ModelAndView list(ModelAndView mv) {
		List<Product> produits = produitService.findAll();
		for (Iterator<Product> iterator = produits.iterator(); iterator.hasNext();) {
			Product product = iterator.next();
			byte[] photo = product.getImage();
			String photoBase64 = Base64Utils.encodeToString(photo);
			product.setImageBase64(photoBase64);
		}
		mv.addObject("produits", produits);
		mv.setViewName("admin/produits/list");
		return mv;
	}

	@GetMapping("/add")
	public ModelAndView add(ModelAndView mv) {
		List<Category> cats = categorieService.findAll();
		List<Marque> marques = marqueService.findAll();
		mv.addObject("produit", new Product());
		mv.addObject("marques", marques);
		mv.addObject("categories", cats);
		mv.setViewName("admin/produits/add");
		return mv;
	}

	@PostMapping("/add")
	public ModelAndView traiterForm(@ModelAttribute("produit") @Valid Product produit, BindingResult errors,
			@RequestParam MultipartFile file, ModelAndView mv) {
		if (errors.hasErrors()) {
			List<Category> cats = categorieService.findAll();
			mv.addObject("categories", cats);
			List<Marque> marques = marqueService.findAll();
			mv.addObject("marques", marques);
			mv.addObject("produit", produit);
			mv.setViewName("admin/produits/add");
		} else {
			try {
				byte[] image = file.getBytes();
				produit.setImage(image);
				produit.setCreationDate(LocalDateTime.now());
				produitService.save(produit);
			} catch (IOException e) {
				e.printStackTrace();
			}

			mv.setViewName("redirect:/admin/produits");
		}
		return mv;
	}

	// /admin/produits/show/{id} /produit/show/1 pour afficher le produit avec l'id
	// 1
	@GetMapping("/show/{id}")
	public ModelAndView show(@PathVariable("id") long prodId, ModelAndView mv) {
		Product produit = produitService.findById(prodId);
		if (produit == null) {
			// rediriger vers "/produits
			mv.setViewName("redirect:/admin/produits");
		} else {
			byte[] photo = produit.getImage();
			String photoBase64 = Base64Utils.encodeToString(photo);
			produit.setImageBase64(photoBase64);
			mv.addObject("produit", produit);
			mv.setViewName("admin/produits/show");
		}
		return mv;
	}

	// /produit/update?id={0}
	@GetMapping("/update")
	public String showUpdate(@RequestParam(required = false) Long id, Model model) {
		// si id == null, redirige l'user vers l'url /produit/list
		if (null == id) {
			return "redirect:/produit/list";
		}
		// récupérer le produit qui a l'id = id
		Product produit = produitService.findById(id);
		byte[] photo = produit.getImage();
		String photoBase64 = Base64Utils.encodeToString(photo);
		produit.setImageBase64(photoBase64);

		List<Category> cats = categorieService.findAll();
		model.addAttribute("categories", cats);
		List<Marque> marques = marqueService.findAll();
		model.addAttribute("marques", marques);
		model.addAttribute("produit", produit);
		return "admin/produits/update";
	}

	@PostMapping("/update/{idProd}")
	public ModelAndView update(@ModelAttribute @Valid Product produit, BindingResult errors,
			@RequestParam MultipartFile file, @RequestParam long id, ModelAndView mv) {
		boolean codebarError = false;
		if (errors.getFieldError("barcode") != null
				&& errors.getFieldError("barcode").getDefaultMessage().equals("Code barre est déjà utilisé !")) {
			String rejectedValue = (String) errors.getFieldError("barcode").getRejectedValue();
			Product prod = produitService.findById(id);
			if (!rejectedValue.equals(prod.getBarcode())) {
				codebarError = true;
			}
		}
		if (errors.hasErrors() && codebarError) {
			if (file.isEmpty()) {
				byte[] photo = produit.getImage();
				String photoBase64 = Base64Utils.encodeToString(photo);
				produit.setImageBase64(photoBase64);
			}
			List<Category> cats = categorieService.findAll();
			mv.addObject("categories", cats);
			List<Marque> marques = marqueService.findAll();
			mv.addObject("marques", marques);
			mv.addObject("produit", produit);
			mv.setViewName("admin/produits/update");
		} else {
			if (!file.isEmpty()) {
				try {
					byte[] image = file.getBytes();
					System.out.println(image.length);
					produit.setImage(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			produit.setUpdatingDate(LocalDateTime.now());
			produitService.update(produit);
			mv.setViewName("redirect:/admin/produits");
		}
		return mv;
	}

	@PostMapping("/delete/{id}")
	@ResponseBody
	public HashMap<String, String> delete(@PathVariable long id) {
		HashMap<String, String> map = new HashMap<>();
		try {
			produitService.delete(id);
			map.put("error", "success");
		} catch (Exception e) {
			map.put("error", e.getMessage());
		}
		return map;
	}
}
