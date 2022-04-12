package com.internship.retail_management.config;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.internship.retail_management.entities.CashRegister;
import com.internship.retail_management.entities.Invoice;
import com.internship.retail_management.entities.InvoicedProduct;
import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.entities.OperatingFund;
import com.internship.retail_management.entities.Product;
import com.internship.retail_management.entities.StockMovement;
import com.internship.retail_management.entities.Store;
import com.internship.retail_management.entities.User;
import com.internship.retail_management.entities.enums.Category;
import com.internship.retail_management.entities.enums.IvaValues;
import com.internship.retail_management.entities.enums.Movement;
import com.internship.retail_management.entities.enums.Status;
import com.internship.retail_management.entities.enums.TransactionType;
import com.internship.retail_management.repositories.CashRegisterRepository;
import com.internship.retail_management.repositories.InvoiceRepository;
import com.internship.retail_management.repositories.InvoicedProductRepository;
import com.internship.retail_management.repositories.IvaRepository;
import com.internship.retail_management.repositories.OperatingFundRepository;
import com.internship.retail_management.repositories.ProductRepository;
import com.internship.retail_management.repositories.StockMovementRepository;
import com.internship.retail_management.repositories.StoreRepository;
import com.internship.retail_management.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired // Injecta a dependência automaticamente com o framework
	private StoreRepository storeRepository;

	@Autowired
	private IvaRepository ivaRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CashRegisterRepository cashRegisterRepository;

	@Autowired
	private UserRepository employeeRepository;

	@Autowired
	private OperatingFundRepository operatingFundRepository;

	@Autowired
	private StockMovementRepository stockMovementRepository;

	@Autowired
	private InvoicedProductRepository invoicedProductRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Override
	public void run(String... args) throws Exception {

		Store s1 = new Store(null, "Rua de cima", "Porto", "1234-123", "221231212", Status.ACTIVE);
		Store s2 = new Store(null, "Rua de baixo", "Gaia", "4321-321", "223212121", Status.INACTIVE);

		storeRepository.saveAll(Arrays.asList(s1, s2));

		CashRegister c1 = new CashRegister(null, s1);
		CashRegister c2 = new CashRegister(null, s1);
		CashRegister c3 = new CashRegister(null, s2);
		CashRegister c4 = new CashRegister(null, s1);
		CashRegister c5 = new CashRegister(null, s2);

		cashRegisterRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));

		User emp1 = new User(null, "Maria", "maria@gmail.com", "123456", "911232112",
				Instant.parse("1974-06-20T19:53:07Z"), 123456789L, Category.EMPLOYEE, Status.ACTIVE, "Rua de cima",
				"Maia", "1234-123", s1);
		User emp2 = new User(null, "Quim", "quim@gmail.com", "123456", "911233112",
				Instant.parse("1980-06-20T19:53:07Z"), 123456784L, Category.EMPLOYEE, Status.ACTIVE, "Rua de baixo",
				"Gaia", "1235-123", s2);

		employeeRepository.saveAll(Arrays.asList(emp1, emp2));

		OperatingFund of1 = new OperatingFund(null, 500.00, 470.00, Instant.parse("2021-06-20T19:53:07Z"), emp2, c1);
		OperatingFund of2 = new OperatingFund(null, 500.00, 530.00, Instant.parse("2021-06-20T19:53:07Z"), emp1, c2);
		OperatingFund of3 = new OperatingFund(null, 470.00, 450.00, Instant.parse("2021-06-22T19:53:07Z"), emp2, c1);

		operatingFundRepository.saveAll(Arrays.asList(of1, of2, of3));

		Iva i1 = new Iva(null, IvaValues.NULL);
		Iva i2 = new Iva(null, IvaValues.LOW);
		Iva i3 = new Iva(null, IvaValues.INTERMEDIATE);
		Iva i4 = new Iva(null, IvaValues.NORMAL);

		ivaRepository.saveAll(Arrays.asList(i1, i2, i3, i4));

		Product p1 = new Product(null, "pao", 10, 0.50, i4);
		Product p2 = new Product(null, "agua", 50, 0.70, i3);
		Product p3 = new Product(null, "manteiga", 10, 0.40, i1);
		Product p4 = new Product(null, "cereais", 20, 1.50, i2);

		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4));

		p1.updateStock(10, Movement.IN);
		p1.updateStock(20, Movement.IN);
		p2.updateStock(5, Movement.IN);
		p3.updateStock(5, Movement.OUT);

		productRepository.saveAll(Arrays.asList(p1, p2, p3));

		List<StockMovement> allMovements = Stream
				.of(p1.getStockMovements(), p2.getStockMovements(), p3.getStockMovements()).flatMap(Collection::stream)
				.collect(Collectors.toList());

		allMovements.forEach(movements -> stockMovementRepository.save(movements));

		Invoice in1 = new Invoice(null, Instant.now(), TransactionType.DEBIT, emp1, c5);
		Invoice in2 = new Invoice(null, Instant.now(), TransactionType.CREDIT, emp2, c2);
		
		invoiceRepository.saveAll(Arrays.asList(in1, in2));
		
		InvoicedProduct ip1 = new InvoicedProduct(null, 5, p1, in1);
		InvoicedProduct ip2 = new InvoicedProduct(null, 10, p2, in1);
		InvoicedProduct ip3 = new InvoicedProduct(null, 10, p4, in2);

		invoicedProductRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		allMovements = Stream
				.of(p1.getStockMovements(), p2.getStockMovements(), p3.getStockMovements()).flatMap(Collection::stream)
				.collect(Collectors.toList());
		
		allMovements.forEach(movements -> stockMovementRepository.save(movements));
		
		p1.updateInvoicedStock();
		p2.updateInvoicedStock();
		p4.updateInvoicedStock();

		productRepository.saveAll(Arrays.asList(p1, p2, p4));
		
	}
}
