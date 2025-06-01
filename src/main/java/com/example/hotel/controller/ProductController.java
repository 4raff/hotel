package com.example.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import com.example.hotel.model.ProductModel;
import com.example.hotel.service.ProductService;

@Controller
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping({"/barang", "/barang/{keyword}"})
    public String index(@PathVariable(required = false) String keyword, Model model) {
        try {
            List<ProductModel> products;

            if (keyword != null && !keyword.isEmpty()) {
                products = service.searchProducts(keyword);
                model.addAttribute("searchKeyword", keyword);
                if (products.isEmpty()) {
                    model.addAttribute("errorMessage", "Barang dengan kata kunci '" + keyword + "' tidak ditemukan!");
                } else {
                    model.addAttribute("successMessage", "Barang berhasil ditemukan!");
                }
            } else {
                products = service.getAllProducts();
            }

            model.addAttribute("products", products);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Terjadi kesalahan saat mencari barang: " + e.getMessage());
            model.addAttribute("products", service.getAllProducts());
        }
        return "view/barang/index";
    }

    @GetMapping("/barang/tambah")
    public String tambah(Model model) {
        return "view/barang/tambah";
    }

    @PostMapping("/barang/tambah")
    public String addProduct(@ModelAttribute ProductModel product, RedirectAttributes redirectAttributes) {
        try {
            service.addProduct(product);
            redirectAttributes.addFlashAttribute("successMessage", "Barang berhasil ditambah!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Barang gagal ditambah!");
        }
        return "redirect:/barang";
    }

    @GetMapping("/barang/hapus/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteProduct(id);
            redirectAttributes.addFlashAttribute("successMessage", "Barang berhasil dihapus!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menghapus barang: " + e.getMessage());
        }
        return "redirect:/barang";
    }
}
