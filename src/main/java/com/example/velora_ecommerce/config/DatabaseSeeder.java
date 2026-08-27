package com.example.velora_ecommerce.config;

import com.example.velora_ecommerce.entities.GiftCard;
import com.example.velora_ecommerce.entities.Product;
import com.example.velora_ecommerce.enums.Brand;
import com.example.velora_ecommerce.enums.CardType;
import com.example.velora_ecommerce.enums.Category;
import com.example.velora_ecommerce.repositories.GiftCardRepository;
import com.example.velora_ecommerce.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final GiftCardRepository giftCardRepository;

    public DatabaseSeeder(ProductRepository productRepository, GiftCardRepository giftCardRepository) {
        this.productRepository = productRepository;
        this.giftCardRepository = giftCardRepository;
    }

    public void addProduct(
            String name,
            Category category,
            Brand brand,
            String description,
            BigDecimal price,
            int quantity,
            String image,
            Map<String, String> specs
    ) {
        Product product = new Product();

        product.setName(name);
        product.setCategory(category);
        product.setBrand(brand);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(quantity);
        product.setImageUrl(image);
        product.setSpecifications(specs);

        productRepository.save(product);
    }

    public void addGiftCard(String code, BigDecimal balance) {
        GiftCard giftCard = new GiftCard();

        giftCard.setCardType(CardType.GIFT_CARD);
        giftCard.setCode(code);
        giftCard.setBalance(balance);

        giftCardRepository.save(giftCard);
    }

    @Override
    public void run(String... args) throws Exception {
        if (giftCardRepository.count() > 0) return;

        // Adding gift cards
        addGiftCard("VELORA-3H7K-91PX", new BigDecimal("10.00"));
        addGiftCard("VELORA-8Q2M-47ZT", new BigDecimal("10.00"));
        addGiftCard("VELORA-5N9R-63VK", new BigDecimal("10.00"));
        addGiftCard("VELORA-1F6P-84WX", new BigDecimal("10.00"));

        addGiftCard("VELORA-7B4L-29QM", new BigDecimal("20.00"));
        addGiftCard("VELORA-2X8C-51RJ", new BigDecimal("20.00"));
        addGiftCard("VELORA-9M3D-76HF", new BigDecimal("20.00"));
        addGiftCard("VELORA-4K7V-18NS", new BigDecimal("20.00"));

        addGiftCard("VELORA-6P2Y-93GL", new BigDecimal("50.00"));
        addGiftCard("VELORA-8T5W-41BQ", new BigDecimal("50.00"));

        if (productRepository.count() > 0) return;

        // Adding keyboards
        addProduct(
                "Vertex Office Pro 104",
                Category.KEYBOARDS,
                Brand.VERTEX,
                "The Vertex Office Pro 104 is a full-size wireless keyboard designed for productivity and everyday use. Its quiet membrane keys provide a comfortable typing experience while the integrated numeric keypad makes data entry fast and efficient. With a slim profile and dependable wireless connection, it's an excellent choice for home offices and business environments.",
                new BigDecimal("24.99"),
                15,
                "/images/keyboards/keyboards 1.png",
                Map.ofEntries(
                        Map.entry("Keyboard Type", "Membrane"),
                        Map.entry("Layout", "Full-size (104 Keys)"),
                        Map.entry("Connectivity", "2.4 GHz Wireless"),
                        Map.entry("Switch Type", "Quiet Membrane"),
                        Map.entry("Backlighting", "None"),
                        Map.entry("Key Rollover", "6-Key"),
                        Map.entry("Hot-Swappable Switches", "No"),
                        Map.entry("Programmable Keys", "No"),
                        Map.entry("Wrist Rest Included", "No"),
                        Map.entry("Dimensions", "17.4 × 5.2 × 0.9 in"),
                        Map.entry("Weight", "1.45 lb"),
                        Map.entry("Color", "Matte Black")
                )
        );

        addProduct(
                "NovaByte Compact 68",
                Category.KEYBOARDS,
                Brand.NOVABYTE,
                "Designed for users who prefer a compact workspace, the NovaByte Compact 68 eliminates the numeric keypad while retaining a comfortable typing layout. Its mechanical switches provide crisp tactile feedback, making it equally suitable for work and gaming.",
                new BigDecimal("35.99"),
                10,
                "/images/keyboards/keyboards 2.png",
                Map.ofEntries(
                        Map.entry("Keyboard Type", "Mechanical"),
                        Map.entry("Layout", "65%"),
                        Map.entry("Connectivity", "Wired USB-C"),
                        Map.entry("Switch Type", "Brown Mechanical Switches"),
                        Map.entry("Backlighting", "White LED"),
                        Map.entry("Key Rollover", "N-Key"),
                        Map.entry("Hot-Swappable Switches", "Yes"),
                        Map.entry("Programmable Keys", "Yes"),
                        Map.entry("Wrist Rest Included", "No"),
                        Map.entry("Dimensions", "12.4 × 4.3 × 1.4 in"),
                        Map.entry("Weight", "1.62 lb"),
                        Map.entry("Color", "Matte Black")
                )
        );

        addProduct(
                "IronCore Apex RGB",
                Category.KEYBOARDS,
                Brand.IRONCORE,
                "Built for enthusiasts, the IronCore Apex RGB combines premium mechanical switches with customizable RGB lighting and durable construction. The black and gray keycap design gives it a professional appearance while vibrant lighting effects make it stand out during gaming sessions.",
                new BigDecimal("59.99"),
                8,
                "/images/keyboards/keyboards 3.png",
                Map.ofEntries(
                        Map.entry("Keyboard Type", "Mechanical"),
                        Map.entry("Layout", "Full-size (104 Keys)"),
                        Map.entry("Connectivity", "Wired USB-C"),
                        Map.entry("Switch Type", "Red Mechanical Switches"),
                        Map.entry("Backlighting", "Per-Key RGB"),
                        Map.entry("Key Rollover", "N-Key"),
                        Map.entry("Hot-Swappable Switches", "Yes"),
                        Map.entry("Programmable Keys", "Yes"),
                        Map.entry("Wrist Rest Included", "No"),
                        Map.entry("Dimensions", "17.6 × 5.4 × 1.5 in"),
                        Map.entry("Weight", "2.28 lb"),
                        Map.entry("Color", "Black / Gray")
                )
        );

        addProduct(
                "Vertex Pure 104",
                Category.KEYBOARDS,
                Brand.VERTEX,
                "The Vertex Pure 104 offers the familiar layout of a full-size keyboard in a clean white finish that complements modern workspaces. Quiet keys and dependable wireless performance make it ideal for professionals, students, and home users alike.",
                new BigDecimal("29.99"),
                20,
                "/images/keyboards/keyboards 4.png",
                Map.ofEntries(
                        Map.entry("Keyboard Type", "Membrane"),
                        Map.entry("Layout", "Full-size (104 Keys)"),
                        Map.entry("Connectivity", "Bluetooth & 2.4 GHz Wireless"),
                        Map.entry("Switch Type", "Quiet Membrane"),
                        Map.entry("Backlighting", "None"),
                        Map.entry("Key Rollover", "6-Key"),
                        Map.entry("Hot-Swappable Switches", "No"),
                        Map.entry("Programmable Keys", "No"),
                        Map.entry("Wrist Rest Included", "No"),
                        Map.entry("Dimensions", "17.4 × 5.2 × 0.9 in"),
                        Map.entry("Weight", "1.43 lb"),
                        Map.entry("Color", "White")
                )
        );

        addProduct(
                "GalaGear Air75",
                Category.KEYBOARDS,
                Brand.GALAGEAR,
                "The GalaGear Air75 delivers a minimalist design without sacrificing performance. Its compact 75% layout saves valuable desk space while maintaining dedicated function keys for productivity. Low-profile mechanical switches provide a smooth, responsive typing experience with a refined modern aesthetic.",
                new BigDecimal("19.99"),
                10,
                "/images/keyboards/keyboards 5.png",
                Map.ofEntries(
                        Map.entry("Keyboard Type", "Low-Profile Mechanical"),
                        Map.entry("Layout", "75%"),
                        Map.entry("Connectivity", "Bluetooth, 2.4 GHz Wireless, USB-C"),
                        Map.entry("Switch Type", "Low-Profile Red Switches"),
                        Map.entry("Backlighting", "White LED"),
                        Map.entry("Key Rollover", "N-Key"),
                        Map.entry("Hot-Swappable Switches", "Yes"),
                        Map.entry("Programmable Keys", "Yes"),
                        Map.entry("Wrist Rest Included", "No"),
                        Map.entry("Dimensions", "12.8 × 5.0 × 1.1 in"),
                        Map.entry("Weight", "1.36 lb"),
                        Map.entry("Color", "White")
                )
        );

        // Adding mice
        addProduct(
                "NovaByte Swift M310",
                Category.MICE,
                Brand.NOVABYTE,
                "The NovaByte Swift M310 is a dependable wireless mouse designed for everyday productivity. Its ergonomic shape provides lasting comfort while quiet buttons and a responsive optical sensor make it an excellent companion for office work, web browsing, and general computing.",
                new BigDecimal("29.99"),
                10,
                "/images/mice/mouse 1.png",
                Map.ofEntries(
                        Map.entry("Mouse Type", "Office"),
                        Map.entry("Connectivity", "2.4 GHz Wireless"),
                        Map.entry("DPI Range", "800–3200 DPI"),
                        Map.entry("Sensor Type", "Optical"),
                        Map.entry("Programmable Buttons", "2"),
                        Map.entry("Polling Rate", "125 Hz"),
                        Map.entry("RGB Lighting", "No"),
                        Map.entry("Battery Life", "Up to 18 months"),
                        Map.entry("Rechargeable", "No (AA Battery)"),
                        Map.entry("Hand Orientation", "Right-handed"),
                        Map.entry("Dimensions", "4.6 × 2.6 × 1.5 in"),
                        Map.entry("Weight", "3.2 oz"),
                        Map.entry("Color", "Matte Black")
                )
        );

        addProduct(
                "Vertex Precision M200",
                Category.MICE,
                Brand.VERTEX,
                "The Vertex Precision M200 combines a minimalist design with dependable wireless performance. Its lightweight construction and precise optical tracking make it ideal for students, professionals, and anyone looking for an affordable everyday mouse.",
                new BigDecimal("24.99"),
                10,
                "/images/mice/mouse 2.png",
                Map.ofEntries(
                        Map.entry("Mouse Type", "Office"),
                        Map.entry("Connectivity", "Bluetooth & 2.4 GHz Wireless"),
                        Map.entry("DPI Range", "800–2400 DPI"),
                        Map.entry("Sensor Type", "Optical"),
                        Map.entry("Programmable Buttons", "0"),
                        Map.entry("Polling Rate", "125 Hz"),
                        Map.entry("RGB Lighting", "No"),
                        Map.entry("Battery Life", "Up to 24 months"),
                        Map.entry("Rechargeable", "No (AA Battery)"),
                        Map.entry("Hand Orientation", "Ambidextrous"),
                        Map.entry("Dimensions", "4.4 × 2.4 × 1.4 in"),
                        Map.entry("Weight", "2.9 oz"),
                        Map.entry("Color", "Matte Black")
                )
        );

        addProduct(
                "IronCore Phantom X7",
                Category.MICE,
                Brand.IRONCORE,
                "Built for competitive gamers, the IronCore Phantom X7 delivers exceptional precision and responsiveness. Featuring an ergonomic gaming design, customizable controls, and a striking red scroll wheel, it offers both performance and aggressive styling for extended gaming sessions.",
                new BigDecimal("44.99"),
                10,
                "/images/mice/mouse 3.png",
                Map.ofEntries(
                        Map.entry("Mouse Type", "Gaming"),
                        Map.entry("Connectivity", "Wired USB-C"),
                        Map.entry("DPI Range", "800–16000 DPI"),
                        Map.entry("Sensor Type", "Optical"),
                        Map.entry("Programmable Buttons", "7"),
                        Map.entry("Polling Rate", "1000 Hz"),
                        Map.entry("RGB Lighting", "No"),
                        Map.entry("Battery Life", "N/A"),
                        Map.entry("Rechargeable", "No"),
                        Map.entry("Hand Orientation", "Right-handed"),
                        Map.entry("Dimensions", "5.0 × 2.8 × 1.6 in"),
                        Map.entry("Weight", "3.8 oz"),
                        Map.entry("Color", "Black / Gray")
                )
        );

        addProduct(
                "GalaGear Luna M450",
                Category.MICE,
                Brand.GALAGEAR,
                "The GalaGear Luna M450 blends elegant aesthetics with premium wireless performance. Its smooth matte finish, ergonomic contours, and quiet switches make it an excellent choice for modern workspaces where both style and comfort matter.",
                new BigDecimal("39.99"),
                10,
                "/images/mice/mouse 4.png",
                Map.ofEntries(
                        Map.entry("Mouse Type", "Ergonomic"),
                        Map.entry("Connectivity", "Bluetooth & 2.4 GHz Wireless"),
                        Map.entry("DPI Range", "800–4000 DPI"),
                        Map.entry("Sensor Type", "Optical"),
                        Map.entry("Programmable Buttons", "2"),
                        Map.entry("Polling Rate", "250 Hz"),
                        Map.entry("RGB Lighting", "No"),
                        Map.entry("Battery Life", "Up to 12 months"),
                        Map.entry("Rechargeable", "Yes (USB-C)"),
                        Map.entry("Hand Orientation", "Right-handed"),
                        Map.entry("Dimensions", "4.8 × 2.7 × 1.6 in"),
                        Map.entry("Weight", "3.3 oz"),
                        Map.entry("Color", "White")
                )
        );

        addProduct(
                "Vertex Air M100",
                Category.MICE,
                Brand.VERTEX,
                "The Vertex Air M100 is a compact wireless mouse built for portability and simplicity. Its lightweight construction slips easily into backpacks and laptop bags, making it the perfect everyday travel companion for work or school.",
                new BigDecimal("19.99"),
                10,
                "/images/mice/mouse 5.png",
                Map.ofEntries(
                        Map.entry("Mouse Type", "Travel"),
                        Map.entry("Connectivity", "Bluetooth"),
                        Map.entry("DPI Range", "800–1600 DPI"),
                        Map.entry("Sensor Type", "Optical"),
                        Map.entry("Programmable Buttons", "0"),
                        Map.entry("Polling Rate", "125 Hz"),
                        Map.entry("RGB Lighting", "No"),
                        Map.entry("Battery Life", "Up to 20 months"),
                        Map.entry("Rechargeable", "No (AA Battery)"),
                        Map.entry("Hand Orientation", "Ambidextrous"),
                        Map.entry("Dimensions", "4.1 × 2.3 × 1.3 in"),
                        Map.entry("Weight", "2.6 oz"),
                        Map.entry("Color", "White")
                )
        );

        // Adding desktop PCs
        addProduct(
                "Vertex Core S1",
                Category.DESKTOPS,
                Brand.VERTEX,
                "The Vertex Core S1 is an affordable desktop built for everyday computing. Whether you're browsing the web, working on documents, streaming media, or attending online classes, it delivers dependable performance in a clean, minimalist tower.",
                new BigDecimal("749.99"),
                10,
                "/images/desktop pcs/desktop pc 1.png",
                Map.ofEntries(
                        Map.entry("CPU Model", "Intel Core i5-14400"),
                        Map.entry("CPU Cores", "10"),
                        Map.entry("CPU Threads", "16"),
                        Map.entry("CPU Clock Speed", "Up to 4.7 GHz"),
                        Map.entry("Graphics Card", "Intel UHD Graphics 730"),
                        Map.entry("Graphics Type", "Integrated"),
                        Map.entry("VRAM", "Shared System Memory"),
                        Map.entry("RAM", "16 GB"),
                        Map.entry("RAM Type", "DDR5"),
                        Map.entry("RAM Speed", "5600 MHz"),
                        Map.entry("Storage Capacity", "512 GB"),
                        Map.entry("Storage Type", "NVMe SSD"),
                        Map.entry("Additional Drive Bays", "2"),
                        Map.entry("Motherboard Chipset", "Intel B760"),
                        Map.entry("Wi-Fi", "Wi-Fi 6"),
                        Map.entry("Bluetooth", "Bluetooth 5.3"),
                        Map.entry("Power Supply", "500W"),
                        Map.entry("Power Supply Certification", "80+ Bronze"),
                        Map.entry("Case Type", "Mid Tower"),
                        Map.entry("Tempered Glass Side Panel", "No"),
                        Map.entry("RGB Lighting", "No"),
                        Map.entry("USB-A Ports", "6"),
                        Map.entry("USB-C Ports", "1"),
                        Map.entry("HDMI", "1"),
                        Map.entry("DisplayPort", "1"),
                        Map.entry("Ethernet", "2.5 Gigabit"),
                        Map.entry("Operating System", "Windows 11 Home"),
                        Map.entry("Dimensions", "17.1 × 8.0 × 16.2 in"),
                        Map.entry("Weight", "16.5 lb"),
                        Map.entry("Color", "Matte Black")
                )
        );

        addProduct(
                "NovaByte ProStation T5",
                Category.DESKTOPS,
                Brand.NOVABYTE,
                "Designed for professionals and multitaskers, the NovaByte ProStation T5 combines modern hardware with a sleek business-oriented design. Its fast processor and generous memory make it an excellent workstation for productivity, software development, and content creation.",
                new BigDecimal("999.99"),
                10,
                "/images/desktop pcs/desktop pc 2.png",
                Map.ofEntries(
                        Map.entry("CPU Model", "Intel Core i7-14700"),
                        Map.entry("CPU Cores", "20"),
                        Map.entry("CPU Threads", "28"),
                        Map.entry("CPU Clock Speed", "Up to 5.4 GHz"),
                        Map.entry("Graphics Card", "NVIDIA RTX 4060"),
                        Map.entry("Graphics Type", "Dedicated"),
                        Map.entry("VRAM", "8 GB GDDR6"),
                        Map.entry("RAM", "32 GB"),
                        Map.entry("RAM Type", "DDR5"),
                        Map.entry("RAM Speed", "6000 MHz"),
                        Map.entry("Storage Capacity", "1 TB"),
                        Map.entry("Storage Type", "NVMe SSD"),
                        Map.entry("Additional Drive Bays", "2"),
                        Map.entry("Motherboard Chipset", "Intel Z790"),
                        Map.entry("Wi-Fi", "Wi-Fi 6E"),
                        Map.entry("Bluetooth", "Bluetooth 5.3"),
                        Map.entry("Power Supply", "650W"),
                        Map.entry("Power Supply Certification", "80+ Gold"),
                        Map.entry("Case Type", "Mid Tower"),
                        Map.entry("Tempered Glass Side Panel", "No"),
                        Map.entry("RGB Lighting", "White Front Accent"),
                        Map.entry("USB-A Ports", "8"),
                        Map.entry("USB-C Ports", "2"),
                        Map.entry("HDMI", "1"),
                        Map.entry("DisplayPort", "3"),
                        Map.entry("Ethernet", "2.5 Gigabit"),
                        Map.entry("Operating System", "Windows 11 Pro"),
                        Map.entry("Dimensions", "18.0 × 8.3 × 17.0 in"),
                        Map.entry("Weight", "19.8 lb"),
                        Map.entry("Color", "Matte Black")
                )
        );

        addProduct(
                "IronCore Titan X9",
                Category.DESKTOPS,
                Brand.IRONCORE,
                "Built for enthusiasts, the IronCore Titan X9 is a premium gaming desktop featuring high-end components and striking RGB cooling. Whether you're gaming in 4K, streaming, or editing video, the Titan X9 delivers uncompromising performance.",
                new BigDecimal("1999.99"),
                10,
                "/images/desktop pcs/desktop pc 3.png",
                Map.ofEntries(
                        Map.entry("CPU Model", "AMD Ryzen 9 9900X"),
                        Map.entry("CPU Cores", "12"),
                        Map.entry("CPU Threads", "24"),
                        Map.entry("CPU Clock Speed", "Up to 5.6 GHz"),
                        Map.entry("Graphics Card", "NVIDIA RTX 5080"),
                        Map.entry("Graphics Type", "Dedicated"),
                        Map.entry("VRAM", "16 GB GDDR7"),
                        Map.entry("RAM", "32 GB"),
                        Map.entry("RAM Type", "DDR5"),
                        Map.entry("RAM Speed", "6400 MHz"),
                        Map.entry("Storage Capacity", "2 TB"),
                        Map.entry("Storage Type", "NVMe Gen4 SSD"),
                        Map.entry("Additional Drive Bays", "3"),
                        Map.entry("Motherboard Chipset", "AMD X870"),
                        Map.entry("Wi-Fi", "Wi-Fi 7"),
                        Map.entry("Bluetooth", "Bluetooth 5.4"),
                        Map.entry("Power Supply", "850W"),
                        Map.entry("Power Supply Certification", "80+ Gold"),
                        Map.entry("Case Type", "Mid Tower"),
                        Map.entry("Tempered Glass Side Panel", "Yes"),
                        Map.entry("RGB Lighting", "Front RGB Fans"),
                        Map.entry("USB-A Ports", "8"),
                        Map.entry("USB-C Ports", "2"),
                        Map.entry("HDMI", "1"),
                        Map.entry("DisplayPort", "3"),
                        Map.entry("Ethernet", "2.5 Gigabit"),
                        Map.entry("Operating System", "Windows 11 Home"),
                        Map.entry("Dimensions", "19.3 × 9.0 × 18.2 in"),
                        Map.entry("Weight", "25.8 lb"),
                        Map.entry("Color", "Black / Gray")
                )
        );

        addProduct(
                "Nimbus Studio S7",
                Category.DESKTOPS,
                Brand.NIMBUS,
                "The Nimbus Studio S7 offers powerful performance in a refined aluminum chassis. Created for creative professionals and office users alike, it combines quiet operation with excellent multitasking performance in an elegant package.",
                new BigDecimal("1299.99"),
                10,
                "/images/desktop pcs/desktop pc 4.png",
                Map.ofEntries(
                        Map.entry("CPU Model", "Intel Core Ultra 7 265"),
                        Map.entry("CPU Cores", "20"),
                        Map.entry("CPU Threads", "20"),
                        Map.entry("CPU Clock Speed", "Up to 5.3 GHz"),
                        Map.entry("Graphics Card", "NVIDIA RTX 4060 Ti"),
                        Map.entry("Graphics Type", "Dedicated"),
                        Map.entry("VRAM", "8 GB GDDR6"),
                        Map.entry("RAM", "32 GB"),
                        Map.entry("RAM Type", "DDR5"),
                        Map.entry("RAM Speed", "6000 MHz"),
                        Map.entry("Storage Capacity", "1 TB"),
                        Map.entry("Storage Type", "NVMe SSD"),
                        Map.entry("Additional Drive Bays", "2"),
                        Map.entry("Motherboard Chipset", "Intel Z890"),
                        Map.entry("Wi-Fi", "Wi-Fi 7"),
                        Map.entry("Bluetooth", "Bluetooth 5.4"),
                        Map.entry("Power Supply", "750W"),
                        Map.entry("Power Supply Certification", "80+ Gold"),
                        Map.entry("Case Type", "Mid Tower"),
                        Map.entry("Tempered Glass Side Panel", "No"),
                        Map.entry("RGB Lighting", "No"),
                        Map.entry("USB-A Ports", "8"),
                        Map.entry("USB-C Ports", "2"),
                        Map.entry("HDMI", "1"),
                        Map.entry("DisplayPort", "3"),
                        Map.entry("Ethernet", "2.5 Gigabit"),
                        Map.entry("Operating System", "Windows 11 Pro"),
                        Map.entry("Dimensions", "17.9 × 8.0 × 16.5 in"),
                        Map.entry("Weight", "18.7 lb"),
                        Map.entry("Color", "Silver")
                )
        );

        addProduct(
                "Vertex Compact C3",
                Category.DESKTOPS,
                Brand.VERTEX,
                "The Vertex Compact C3 is a space-saving desktop that delivers reliable everyday performance without occupying much desk space. Its clean silver-and-black design fits naturally into both home and office environments.",
                new BigDecimal("849.99"),
                10,
                "/images/desktop pcs/desktop pc 5.png",
                Map.ofEntries(
                        Map.entry("CPU Model", "Intel Core i5-14400"),
                        Map.entry("CPU Cores", "10"),
                        Map.entry("CPU Threads", "16"),
                        Map.entry("CPU Clock Speed", "Up to 4.7 GHz"),
                        Map.entry("Graphics Card", "Intel UHD Graphics 730"),
                        Map.entry("Graphics Type", "Integrated"),
                        Map.entry("VRAM", "Shared System Memory"),
                        Map.entry("RAM", "16 GB"),
                        Map.entry("RAM Type", "DDR5"),
                        Map.entry("RAM Speed", "5600 MHz"),
                        Map.entry("Storage Capacity", "1 TB"),
                        Map.entry("Storage Type", "NVMe SSD"),
                        Map.entry("Additional Drive Bays", "1"),
                        Map.entry("Motherboard Chipset", "Intel B760"),
                        Map.entry("Wi-Fi", "Wi-Fi 6"),
                        Map.entry("Bluetooth", "Bluetooth 5.3"),
                        Map.entry("Power Supply", "500W"),
                        Map.entry("Power Supply Certification", "80+ Bronze"),
                        Map.entry("Case Type", "Compact Tower"),
                        Map.entry("Tempered Glass Side Panel", "No"),
                        Map.entry("RGB Lighting", "No"),
                        Map.entry("USB-A Ports", "6"),
                        Map.entry("USB-C Ports", "1"),
                        Map.entry("HDMI", "1"),
                        Map.entry("DisplayPort", "1"),
                        Map.entry("Ethernet", "Gigabit Ethernet"),
                        Map.entry("Operating System", "Windows 11 Home"),
                        Map.entry("Dimensions", "15.2 × 6.5 × 13.8 in"),
                        Map.entry("Weight", "13.6 lb"),
                        Map.entry("Color", "Silver / Black")
                )
        );

        // Adding monitors
        addProduct(
                "GreenPeak Horizon Ultra 34",
                Category.MONITORS,
                Brand.GREENPEAK,
                "The GreenPeak Horizon Ultra 34 is an immersive ultrawide curved monitor designed for gaming, content creation, and multitasking. Its expansive display, smooth refresh rate, and vibrant color reproduction provide an exceptional viewing experience.",
                new BigDecimal("349.99"),
                10,
                "/images/monitors/monitor 1.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "34 in"),
                        Map.entry("Resolution", "3440 × 1440"),
                        Map.entry("Refresh Rate", "165 Hz"),
                        Map.entry("Response Time", "1 ms"),
                        Map.entry("Panel Type", "VA"),
                        Map.entry("Aspect Ratio", "21:9"),
                        Map.entry("Brightness", "400 nits"),
                        Map.entry("Contrast Ratio", "3000:1"),
                        Map.entry("Color Gamut", "95% DCI-P3"),
                        Map.entry("HDR Support", "HDR400"),
                        Map.entry("Curved or Flat", "Curved"),
                        Map.entry("Curvature", "1500R"),
                        Map.entry("Adaptive Sync", "FreeSync Premium"),
                        Map.entry("VESA Mount Compatible", "Yes"),
                        Map.entry("Height Adjustment", "Yes"),
                        Map.entry("Tilt Adjustment", "Yes"),
                        Map.entry("Swivel Adjustment", "Yes"),
                        Map.entry("Built-in Speakers", "No"),
                        Map.entry("Ports", "2 HDMI, 2 DisplayPort, USB Hub"),
                        Map.entry("Dimensions", "31.8 × 14.3 × 4.7 in"),
                        Map.entry("Weight", "15.9 lb"),
                        Map.entry("Color", "Black")
                )
        );

        addProduct(
                "Vertex Vision 27",
                Category.MONITORS,
                Brand.VERTEX,
                "The Vertex Vision 27 is a sleek everyday monitor built for productivity, streaming, and light creative work. Its IPS panel delivers vibrant colors and wide viewing angles while maintaining a clean modern aesthetic.",
                new BigDecimal("199.99"),
                10,
                "/images/monitors/monitor 2.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "27 in"),
                        Map.entry("Resolution", "2560 × 1440"),
                        Map.entry("Refresh Rate", "75 Hz"),
                        Map.entry("Response Time", "5 ms"),
                        Map.entry("Panel Type", "IPS"),
                        Map.entry("Aspect Ratio", "16:9"),
                        Map.entry("Brightness", "350 nits"),
                        Map.entry("Contrast Ratio", "1000:1"),
                        Map.entry("Color Gamut", "99% sRGB"),
                        Map.entry("HDR Support", "No"),
                        Map.entry("Curved or Flat", "Flat"),
                        Map.entry("Adaptive Sync", "None"),
                        Map.entry("VESA Mount Compatible", "Yes"),
                        Map.entry("Height Adjustment", "Yes"),
                        Map.entry("Tilt Adjustment", "Yes"),
                        Map.entry("Swivel Adjustment", "No"),
                        Map.entry("Built-in Speakers", "Yes"),
                        Map.entry("Ports", "2 HDMI, DisplayPort"),
                        Map.entry("Dimensions", "24.1 × 14.0 × 2.0 in"),
                        Map.entry("Weight", "11.2 lb"),
                        Map.entry("Color", "Black")
                )
        );

        addProduct(
                "Vertex StudioView 32",
                Category.MONITORS,
                Brand.VERTEX,
                "The Vertex StudioView 32 combines a premium aluminum finish with exceptional image quality, making it ideal for designers, photographers, and professionals seeking accurate color reproduction.",
                new BigDecimal("249.99"),
                10,
                "/images/monitors/monitor 3.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "32 in"),
                        Map.entry("Resolution", "3840 × 2160 (4K)"),
                        Map.entry("Refresh Rate", "60 Hz"),
                        Map.entry("Response Time", "5 ms"),
                        Map.entry("Panel Type", "IPS"),
                        Map.entry("Aspect Ratio", "16:9"),
                        Map.entry("Brightness", "400 nits"),
                        Map.entry("Contrast Ratio", "1200:1"),
                        Map.entry("Color Gamut", "99% Adobe RGB"),
                        Map.entry("HDR Support", "HDR400"),
                        Map.entry("Curved or Flat", "Flat"),
                        Map.entry("Adaptive Sync", "None"),
                        Map.entry("VESA Mount Compatible", "Yes"),
                        Map.entry("Height Adjustment", "Yes"),
                        Map.entry("Tilt Adjustment", "Yes"),
                        Map.entry("Swivel Adjustment", "Yes"),
                        Map.entry("Built-in Speakers", "Yes"),
                        Map.entry("Ports", "HDMI, DisplayPort, USB-C"),
                        Map.entry("Dimensions", "28.1 × 16.8 × 2.2 in"),
                        Map.entry("Weight", "14.8 lb"),
                        Map.entry("Color", "Black / White")
                )
        );

        addProduct(
                "Nimbus Clarity 27",
                Category.MONITORS,
                Brand.NIMBUS,
                "The Nimbus Clarity 27 is a refined monitor built for professionals who value elegant design and exceptional display quality. Its ultra-thin bezels and aluminum finish complement any modern workspace.",
                new BigDecimal("279.99"),
                10,
                "/images/monitors/monitor 4.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "27 in"),
                        Map.entry("Resolution", "3840 × 2160 (4K)"),
                        Map.entry("Refresh Rate", "60 Hz"),
                        Map.entry("Response Time", "5 ms"),
                        Map.entry("Panel Type", "IPS"),
                        Map.entry("Aspect Ratio", "16:9"),
                        Map.entry("Brightness", "400 nits"),
                        Map.entry("Contrast Ratio", "1200:1"),
                        Map.entry("Color Gamut", "100% sRGB"),
                        Map.entry("HDR Support", "HDR400"),
                        Map.entry("Curved or Flat", "Flat"),
                        Map.entry("Adaptive Sync", "None"),
                        Map.entry("VESA Mount Compatible", "Yes"),
                        Map.entry("Height Adjustment", "Yes"),
                        Map.entry("Tilt Adjustment", "Yes"),
                        Map.entry("Swivel Adjustment", "Yes"),
                        Map.entry("Built-in Speakers", "Yes"),
                        Map.entry("Ports", "HDMI, DisplayPort, USB-C"),
                        Map.entry("Dimensions", "24.0 × 14.0 × 1.8 in"),
                        Map.entry("Weight", "10.9 lb"),
                        Map.entry("Color", "Silver")
                )
        );

        addProduct(
                "Arctik Apex 32",
                Category.MONITORS,
                Brand.ARCTIK,
                "Designed with gamers in mind, the Arctik Apex 32 delivers fluid gameplay and vibrant visuals. Its bold stand and fast refresh rate make it an excellent centerpiece for any gaming setup.",
                new BigDecimal("329.99"),
                10,
                "/images/monitors/monitor 5.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "32 in"),
                        Map.entry("Resolution", "2560 × 1440"),
                        Map.entry("Refresh Rate", "180 Hz"),
                        Map.entry("Response Time", "1 ms"),
                        Map.entry("Panel Type", "Fast IPS"),
                        Map.entry("Aspect Ratio", "16:9"),
                        Map.entry("Brightness", "450 nits"),
                        Map.entry("Contrast Ratio", "1200:1"),
                        Map.entry("Color Gamut", "98% DCI-P3"),
                        Map.entry("HDR Support", "HDR600"),
                        Map.entry("Curved or Flat", "Flat"),
                        Map.entry("Adaptive Sync", "FreeSync Premium & G-SYNC Compatible"),
                        Map.entry("VESA Mount Compatible", "Yes"),
                        Map.entry("Height Adjustment", "Yes"),
                        Map.entry("Tilt Adjustment", "Yes"),
                        Map.entry("Swivel Adjustment", "Yes"),
                        Map.entry("Built-in Speakers", "No"),
                        Map.entry("Ports", "2 HDMI, DisplayPort, USB Hub"),
                        Map.entry("Dimensions", "28.3 × 16.7 × 2.1 in"),
                        Map.entry("Weight", "13.8 lb"),
                        Map.entry("Color", "Silver / Black")
                )
        );

        // Adding laptops
        addProduct(
                "Vertex EliteBook 15",
                Category.LAPTOPS,
                Brand.VERTEX,
                "The Vertex EliteBook 15 is a premium productivity laptop designed for professionals who need dependable performance for multitasking, office applications, and light creative workloads. Its sleek matte black chassis and lightweight design make it an excellent companion for work on the go.",
                new BigDecimal("749.99"),
                10,
                "/images/laptops/laptop 1.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "15.6 in"),
                        Map.entry("Resolution", "1920 × 1080 (Full HD)"),
                        Map.entry("Refresh Rate", "60 Hz"),
                        Map.entry("Processor", "Intel Core Ultra 7 255H"),
                        Map.entry("Graphics", "Intel Arc Graphics"),
                        Map.entry("RAM", "16 GB DDR5"),
                        Map.entry("Storage", "1 TB NVMe SSD"),
                        Map.entry("Operating System", "Windows 11 Pro"),
                        Map.entry("Battery Life", "Up to 12 hours"),
                        Map.entry("Keyboard", "Backlit"),
                        Map.entry("Webcam", "1080p"),
                        Map.entry("Wireless", "Wi-Fi 7, Bluetooth 5.4"),
                        Map.entry("Ports", "2 USB-C, 2 USB-A, HDMI, Audio Jack"),
                        Map.entry("Weight", "3.8 lb"),
                        Map.entry("Color", "Black")
                )
        );

        addProduct(
                "NovaByte Fusion 16",
                Category.LAPTOPS,
                Brand.NOVABYTE,
                "Built for creators and power users, the NovaByte Fusion 16 combines a high-performance processor with dedicated graphics to tackle demanding applications, gaming, and content creation with ease.",
                new BigDecimal("899.99"),
                10,
                "/images/laptops/laptop 2.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "16.0 in"),
                        Map.entry("Resolution", "2560 × 1600"),
                        Map.entry("Refresh Rate", "165 Hz"),
                        Map.entry("Processor", "Intel Core Ultra 9 285H"),
                        Map.entry("Graphics", "NVIDIA RTX 5070 Laptop"),
                        Map.entry("RAM", "32 GB DDR5"),
                        Map.entry("Storage", "2 TB NVMe SSD"),
                        Map.entry("Operating System", "Windows 11 Home"),
                        Map.entry("Battery Life", "Up to 9 hours"),
                        Map.entry("Keyboard", "RGB Backlit"),
                        Map.entry("Webcam", "1080p"),
                        Map.entry("Wireless", "Wi-Fi 7, Bluetooth 5.4"),
                        Map.entry("Ports", "2 USB-C, 3 USB-A, HDMI, Ethernet"),
                        Map.entry("Weight", "5.2 lb"),
                        Map.entry("Color", "Black")
                )
        );

        addProduct(
                "Arctik Voyager 15",
                Category.LAPTOPS,
                Brand.ARCTIK,
                "The Arctik Voyager 15 delivers dependable everyday performance in a modern black-and-silver design. Ideal for students and home users, it offers long battery life and responsive performance for productivity and entertainment.",
                new BigDecimal("699.99"),
                10,
                "/images/laptops/laptop 3.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "15.6 in"),
                        Map.entry("Resolution", "1920 × 1080 (Full HD)"),
                        Map.entry("Refresh Rate", "60 Hz"),
                        Map.entry("Processor", "AMD Ryzen 7 8840U"),
                        Map.entry("Graphics", "AMD Radeon 780M"),
                        Map.entry("RAM", "16 GB LPDDR5X"),
                        Map.entry("Storage", "512 GB NVMe SSD"),
                        Map.entry("Operating System", "Windows 11 Home"),
                        Map.entry("Battery Life", "Up to 13 hours"),
                        Map.entry("Keyboard", "Backlit"),
                        Map.entry("Webcam", "1080p"),
                        Map.entry("Wireless", "Wi-Fi 6E, Bluetooth 5.3"),
                        Map.entry("Ports", "USB-C, 2 USB-A, HDMI"),
                        Map.entry("Weight", "3.7 lb"),
                        Map.entry("Color", "Black / Silver")
                )
        );

        addProduct(
                "Nimbus Air 14",
                Category.LAPTOPS,
                Brand.NIMBUS,
                "The Nimbus Air 14 emphasizes elegance and portability without sacrificing performance. Featuring an all-aluminum silver chassis and long battery life, it is perfect for professionals and students alike.",
                new BigDecimal("829.99"),
                10,
                "/images/laptops/laptop 4.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "14.0 in"),
                        Map.entry("Resolution", "2880 × 1800"),
                        Map.entry("Refresh Rate", "120 Hz"),
                        Map.entry("Processor", "Intel Core Ultra 7 265U"),
                        Map.entry("Graphics", "Intel Arc Graphics"),
                        Map.entry("RAM", "16 GB LPDDR5X"),
                        Map.entry("Storage", "1 TB NVMe SSD"),
                        Map.entry("Operating System", "Windows 11 Pro"),
                        Map.entry("Battery Life", "Up to 15 hours"),
                        Map.entry("Keyboard", "Backlit"),
                        Map.entry("Webcam", "1440p"),
                        Map.entry("Wireless", "Wi-Fi 7, Bluetooth 5.4"),
                        Map.entry("Ports", "2 Thunderbolt 4, USB-A, HDMI"),
                        Map.entry("Weight", "3.1 lb"),
                        Map.entry("Color", "Silver")
                )
        );

        addProduct(
                "Vertex StudioBook 15",
                Category.LAPTOPS,
                Brand.VERTEX,
                "Designed for professionals and creators, the Vertex StudioBook 15 pairs a premium aluminum chassis with dedicated graphics to handle creative applications, multitasking, and everyday productivity.",
                new BigDecimal("1099.99"),
                10,
                "/images/laptops/laptop 5.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "15.6 in"),
                        Map.entry("Resolution", "2560 × 1440"),
                        Map.entry("Refresh Rate", "120 Hz"),
                        Map.entry("Processor", "Intel Core Ultra 7 265H"),
                        Map.entry("Graphics", "NVIDIA RTX 4060 Laptop"),
                        Map.entry("RAM", "32 GB DDR5"),
                        Map.entry("Storage", "1 TB NVMe SSD"),
                        Map.entry("Operating System", "Windows 11 Pro"),
                        Map.entry("Battery Life", "Up to 11 hours"),
                        Map.entry("Keyboard", "White Backlit"),
                        Map.entry("Webcam", "1080p"),
                        Map.entry("Wireless", "Wi-Fi 7, Bluetooth 5.4"),
                        Map.entry("Ports", "2 Thunderbolt 4, 2 USB-A, HDMI"),
                        Map.entry("Weight", "4.1 lb"),
                        Map.entry("Color", "Silver / Black")
                )
        );

        // Adding tablets
        addProduct(
                "NovaByte Slate 10",
                Category.TABLETS,
                Brand.NOVABYTE,
                "The NovaByte Slate 10 is a lightweight tablet designed for entertainment, web browsing, note-taking, and everyday productivity. Its long battery life and responsive touchscreen make it an excellent companion for home and travel.",
                new BigDecimal("299.99"),
                10,
                "/images/tablets/tablet 1.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "10.9 in"),
                        Map.entry("Resolution", "2000 × 1200"),
                        Map.entry("Refresh Rate", "60 Hz"),
                        Map.entry("Display Type", "IPS LCD"),
                        Map.entry("Processor", "NovaByte N1"),
                        Map.entry("RAM", "8 GB"),
                        Map.entry("Storage", "128 GB"),
                        Map.entry("Expandable Storage", "microSD up to 1 TB"),
                        Map.entry("Rear Camera", "8 MP"),
                        Map.entry("Front Camera", "5 MP"),
                        Map.entry("Battery Life", "Up to 11 hours"),
                        Map.entry("Operating System", "Android 16"),
                        Map.entry("Wireless", "Wi-Fi 6, Bluetooth 5.3"),
                        Map.entry("Biometric Security", "Face Unlock"),
                        Map.entry("Stylus Support", "No"),
                        Map.entry("Keyboard Support", "No"),
                        Map.entry("Ports", "USB-C"),
                        Map.entry("Weight", "1.05 lb"),
                        Map.entry("Color", "Black")
                )
        );

        addProduct(
                "Arctik Tab 10",
                Category.TABLETS,
                Brand.ARCTIK,
                "The Arctik Tab 10 delivers dependable performance in a sleek, modern design. Ideal for students and families, it handles streaming, browsing, and light productivity with ease.",
                new BigDecimal("329.99"),
                10,
                "/images/tablets/tablet 2.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "10.9 in"),
                        Map.entry("Resolution", "2360 × 1640"),
                        Map.entry("Refresh Rate", "60 Hz"),
                        Map.entry("Display Type", "IPS LCD"),
                        Map.entry("Processor", "Arctik A2"),
                        Map.entry("RAM", "8 GB"),
                        Map.entry("Storage", "128 GB"),
                        Map.entry("Expandable Storage", "microSD up to 1 TB"),
                        Map.entry("Rear Camera", "8 MP"),
                        Map.entry("Front Camera", "8 MP"),
                        Map.entry("Battery Life", "Up to 12 hours"),
                        Map.entry("Operating System", "Android 16"),
                        Map.entry("Wireless", "Wi-Fi 6E, Bluetooth 5.4"),
                        Map.entry("Biometric Security", "Fingerprint Sensor"),
                        Map.entry("Stylus Support", "Yes"),
                        Map.entry("Keyboard Support", "No"),
                        Map.entry("Ports", "USB-C"),
                        Map.entry("Weight", "1.02 lb"),
                        Map.entry("Color", "Black")
                )
        );

        addProduct(
                "NovaByte Slate Pro 12",
                Category.TABLETS,
                Brand.NOVABYTE,
                "Designed for creators and multitaskers, the NovaByte Slate Pro 12 features a larger high-refresh-rate display, improved cameras, and enhanced stylus support for drawing, editing, and note-taking.",
                new BigDecimal("599.99"),
                10,
                "/images/tablets/tablet 3.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "12.4 in"),
                        Map.entry("Resolution", "2800 × 1752"),
                        Map.entry("Refresh Rate", "120 Hz"),
                        Map.entry("Display Type", "OLED"),
                        Map.entry("Processor", "NovaByte N2 Pro"),
                        Map.entry("RAM", "12 GB"),
                        Map.entry("Storage", "256 GB"),
                        Map.entry("Expandable Storage", "microSD up to 2 TB"),
                        Map.entry("Rear Camera", "13 MP"),
                        Map.entry("Front Camera", "12 MP Ultra-Wide"),
                        Map.entry("Battery Life", "Up to 13 hours"),
                        Map.entry("Operating System", "Android 16"),
                        Map.entry("Wireless", "Wi-Fi 7, Bluetooth 5.4"),
                        Map.entry("Biometric Security", "Fingerprint Sensor"),
                        Map.entry("Stylus Support", "Yes"),
                        Map.entry("Keyboard Support", "Yes"),
                        Map.entry("Ports", "USB-C"),
                        Map.entry("Weight", "1.28 lb"),
                        Map.entry("Color", "Black")
                )
        );

        addProduct(
                "Arctik Tab Plus 12",
                Category.TABLETS,
                Brand.ARCTIK,
                "The Arctik Tab Plus 12 combines premium build quality with a vivid high-resolution display and advanced stylus support, making it an excellent choice for students, artists, and professionals.",
                new BigDecimal("649.99"),
                10,
                "/images/tablets/tablet 4.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "12.4 in"),
                        Map.entry("Resolution", "2800 × 1752"),
                        Map.entry("Refresh Rate", "120 Hz"),
                        Map.entry("Display Type", "OLED"),
                        Map.entry("Processor", "Arctik A3"),
                        Map.entry("RAM", "12 GB"),
                        Map.entry("Storage", "256 GB"),
                        Map.entry("Expandable Storage", "microSD up to 2 TB"),
                        Map.entry("Rear Camera", "13 MP"),
                        Map.entry("Front Camera", "12 MP"),
                        Map.entry("Battery Life", "Up to 14 hours"),
                        Map.entry("Operating System", "Android 16"),
                        Map.entry("Wireless", "Wi-Fi 7, Bluetooth 5.4"),
                        Map.entry("Biometric Security", "Fingerprint Sensor"),
                        Map.entry("Stylus Support", "Yes"),
                        Map.entry("Keyboard Support", "Yes"),
                        Map.entry("Ports", "USB-C"),
                        Map.entry("Weight", "1.24 lb"),
                        Map.entry("Color", "Silver")
                )
        );

        addProduct(
                "NovaByte Slate Enterprise 13",
                Category.TABLETS,
                Brand.NOVABYTE,
                "Built for professionals on the move, the NovaByte Slate Enterprise 13 functions as both a powerful tablet and a lightweight workstation. With support for detachable keyboards, stylus input, enterprise security, and desktop-class multitasking, it is ideal for business users and field professionals.",
                new BigDecimal("899.99"),
                10,
                "/images/tablets/tablet 5.png",
                Map.ofEntries(
                        Map.entry("Screen Size", "13.0 in"),
                        Map.entry("Resolution", "2880 × 1920"),
                        Map.entry("Refresh Rate", "120 Hz"),
                        Map.entry("Display Type", "OLED"),
                        Map.entry("Processor", "Intel Core Ultra 5 236V"),
                        Map.entry("Graphics", "Intel Arc Graphics"),
                        Map.entry("RAM", "16 GB LPDDR5X"),
                        Map.entry("Storage", "512 GB NVMe SSD"),
                        Map.entry("Rear Camera", "13 MP"),
                        Map.entry("Front Camera", "10 MP"),
                        Map.entry("Battery Life", "Up to 15 hours"),
                        Map.entry("Operating System", "Windows 11 Pro"),
                        Map.entry("Wireless", "Wi-Fi 7, Bluetooth 5.4"),
                        Map.entry("Biometric Security", "Windows Hello Facial Recognition"),
                        Map.entry("Stylus Support", "Yes"),
                        Map.entry("Keyboard Support", "Detachable Keyboard Included"),
                        Map.entry("Ports", "2 Thunderbolt 4"),
                        Map.entry("Weight", "1.78 lb"),
                        Map.entry("Color", "Black")
                )
        );

        // Adding headphones & earbuds
        addProduct(
                "Resona Pulse H500",
                Category.ACCESSORIES,
                Brand.RESONA,
                "The Resona Pulse H500 delivers premium wireless audio with deep bass, crystal-clear vocals, and all-day comfort. Its lightweight design and active noise cancellation make it an excellent choice for commuting, travel, and everyday listening.",
                new BigDecimal("179.99"),
                10,
                "/images/headphones and earbuds/headphones 1.png",
                Map.ofEntries(
                        Map.entry("Type", "Over-Ear Wireless"),
                        Map.entry("Connectivity", "Bluetooth 5.4"),
                        Map.entry("Driver Size", "40 mm"),
                        Map.entry("Frequency Response", "20 Hz – 20 kHz"),
                        Map.entry("Noise Cancellation", "Active Noise Cancellation"),
                        Map.entry("Microphone", "Dual Beamforming"),
                        Map.entry("Battery Life", "Up to 40 hours"),
                        Map.entry("Fast Charging", "Yes"),
                        Map.entry("Charging Port", "USB-C"),
                        Map.entry("Voice Assistant Support", "Yes"),
                        Map.entry("Foldable", "Yes"),
                        Map.entry("Weight", "9.4 oz"),
                        Map.entry("Color", "Black / Silver")
                )
        );

        addProduct(
                "Auralis Luxe One",
                Category.ACCESSORIES,
                Brand.AURALIS,
                "Crafted with luxurious materials and premium sound quality, the Auralis Luxe One combines elegant styling with immersive audio. Rich detail, exceptional comfort, and industry-leading noise cancellation make it perfect for discerning listeners.",
                new BigDecimal("299.99"),
                10,
                "/images/headphones and earbuds/headphones 2.png",
                Map.ofEntries(
                        Map.entry("Type", "Over-Ear Wireless"),
                        Map.entry("Connectivity", "Bluetooth 5.4"),
                        Map.entry("Driver Size", "42 mm"),
                        Map.entry("Frequency Response", "18 Hz – 22 kHz"),
                        Map.entry("Noise Cancellation", "Adaptive ANC"),
                        Map.entry("Microphone", "Triple Beamforming"),
                        Map.entry("Battery Life", "Up to 45 hours"),
                        Map.entry("Fast Charging", "Yes"),
                        Map.entry("Charging Port", "USB-C"),
                        Map.entry("Voice Assistant Support", "Yes"),
                        Map.entry("Foldable", "Yes"),
                        Map.entry("Weight", "9.0 oz"),
                        Map.entry("Color", "White / Gold")
                )
        );

        addProduct(
                "Resona Wave H700",
                Category.ACCESSORIES,
                Brand.RESONA,
                "The Resona Wave H700 blends vibrant styling with premium sound quality. Designed for music lovers and gamers alike, it offers immersive stereo sound, low-latency wireless connectivity, and exceptional comfort.",
                new BigDecimal("229.99"),
                10,
                "/images/headphones and earbuds/headphones 3.png",
                Map.ofEntries(
                        Map.entry("Type", "Over-Ear Wireless"),
                        Map.entry("Connectivity", "Bluetooth 5.4"),
                        Map.entry("Driver Size", "45 mm"),
                        Map.entry("Frequency Response", "20 Hz – 22 kHz"),
                        Map.entry("Noise Cancellation", "Hybrid ANC"),
                        Map.entry("Microphone", "Dual Beamforming"),
                        Map.entry("Battery Life", "Up to 42 hours"),
                        Map.entry("Fast Charging", "Yes"),
                        Map.entry("Charging Port", "USB-C"),
                        Map.entry("Voice Assistant Support", "Yes"),
                        Map.entry("Foldable", "Yes"),
                        Map.entry("Weight", "9.7 oz"),
                        Map.entry("Color", "Blue / Turquoise")
                )
        );

        addProduct(
                "Resona AirBuds",
                Category.ACCESSORIES,
                Brand.RESONA,
                "The Resona AirBuds provide rich, balanced sound in a compact truly wireless design. Their pocket-sized charging case and dependable battery life make them perfect for everyday listening.",
                new BigDecimal("79.99"),
                10,
                "/images/headphones and earbuds/earbuds 1.png",
                Map.ofEntries(
                        Map.entry("Type", "True Wireless Earbuds"),
                        Map.entry("Connectivity", "Bluetooth 5.4"),
                        Map.entry("Driver Size", "10 mm"),
                        Map.entry("Frequency Response", "20 Hz – 20 kHz"),
                        Map.entry("Noise Cancellation", "Environmental Noise Cancellation"),
                        Map.entry("Microphone", "Dual Microphones"),
                        Map.entry("Battery Life", "8 hours (32 hours with case)"),
                        Map.entry("Fast Charging", "Yes"),
                        Map.entry("Charging Port", "USB-C"),
                        Map.entry("Wireless Charging", "No"),
                        Map.entry("Water Resistance", "IPX5"),
                        Map.entry("Weight", "1.8 oz (with case)"),
                        Map.entry("Color", "Black")
                )
        );

        addProduct(
                "Auralis AuraPods",
                Category.ACCESSORIES,
                Brand.AURALIS,
                "Designed to complement the Auralis luxury lineup, the AuraPods feature premium sound, adaptive noise cancellation, and an elegant charging case. Their refined tuning and exceptional comfort provide a first-class listening experience.",
                new BigDecimal("169.99"),
                10,
                "/images/headphones and earbuds/earbuds 2.png",
                Map.ofEntries(
                        Map.entry("Type", "True Wireless Earbuds"),
                        Map.entry("Connectivity", "Bluetooth 5.4"),
                        Map.entry("Driver Size", "11 mm"),
                        Map.entry("Frequency Response", "18 Hz – 22 kHz"),
                        Map.entry("Noise Cancellation", "Adaptive ANC"),
                        Map.entry("Microphone", "Triple Microphones"),
                        Map.entry("Battery Life", "9 hours (36 hours with case)"),
                        Map.entry("Fast Charging", "Yes"),
                        Map.entry("Charging Port", "USB-C"),
                        Map.entry("Wireless Charging", "Yes"),
                        Map.entry("Water Resistance", "IPX5"),
                        Map.entry("Weight", "2.0 oz (with case)"),
                        Map.entry("Color", "Red")
                )
        );

        // Adding HDMI cables
        addProduct(
                "Resona Link HDMI 2.1",
                Category.ACCESSORIES,
                Brand.RESONA,
                "The Resona Link HDMI 2.1 cable delivers reliable high-speed connectivity for gaming consoles, PCs, TVs, and monitors. Supporting ultra-high resolutions and refresh rates, it provides crisp video and immersive audio while maintaining excellent signal integrity.",
                new BigDecimal("14.99"),
                10,
                "/images/hdmi cables/hdmi 1.png",
                Map.ofEntries(
                        Map.entry("Cable Type", "HDMI 2.1"),
                        Map.entry("Cable Length", "6 ft"),
                        Map.entry("Maximum Resolution", "8K @ 60 Hz, 4K @ 120 Hz"),
                        Map.entry("Maximum Bandwidth", "48 Gbps"),
                        Map.entry("Connector Type", "HDMI Type-A Male to HDMI Type-A Male"),
                        Map.entry("Cable Jacket", "PVC"),
                        Map.entry("Connector Plating", "Gold-Plated"),
                        Map.entry("HDR Support", "Yes"),
                        Map.entry("eARC Support", "Yes"),
                        Map.entry("HDCP Support", "HDCP 2.3"),
                        Map.entry("Variable Refresh Rate (VRR)", "Yes"),
                        Map.entry("Auto Low Latency Mode (ALLM)", "Yes"),
                        Map.entry("Color", "Black")
                )
        );

        addProduct(
                "GreenPeak UltraFlex HDMI 2.1",
                Category.ACCESSORIES,
                Brand.GREENPEAK,
                "Built for premium home theater and gaming setups, the GreenPeak UltraFlex HDMI 2.1 cable features a durable braided exterior, gold-plated connectors, and support for the latest HDMI features to ensure maximum performance and longevity.",
                new BigDecimal("24.99"),
                10,
                "/images/hdmi cables/hdmi 2.png",
                Map.ofEntries(
                        Map.entry("Cable Type", "HDMI 2.1"),
                        Map.entry("Cable Length", "10 ft"),
                        Map.entry("Maximum Resolution", "8K @ 60 Hz, 4K @ 120 Hz"),
                        Map.entry("Maximum Bandwidth", "48 Gbps"),
                        Map.entry("Connector Type", "HDMI Type-A Male to HDMI Type-A Male"),
                        Map.entry("Cable Jacket", "Braided Nylon"),
                        Map.entry("Connector Plating", "Gold-Plated"),
                        Map.entry("HDR Support", "Yes"),
                        Map.entry("eARC Support", "Yes"),
                        Map.entry("HDCP Support", "HDCP 2.3"),
                        Map.entry("Variable Refresh Rate (VRR)", "Yes"),
                        Map.entry("Auto Low Latency Mode (ALLM)", "Yes"),
                        Map.entry("Color", "Black")
                )
        );

        // Adding speakers
        addProduct(
                "GalaGear AudioCore S200",
                Category.ACCESSORIES,
                Brand.GALAGEAR,
                "The GalaGear AudioCore S200 desktop speakers combine elegant styling with rich stereo sound, making them ideal for music, movies, gaming, and everyday desktop use. Their clean, modern design fits seamlessly into both home and office workspaces.",
                new BigDecimal("79.99"),
                10,
                "/images/speakers/speaker 1.png",
                Map.ofEntries(
                        Map.entry("Speaker Type", "2.0 Stereo Desktop Speakers"),
                        Map.entry("Connectivity", "3.5 mm Audio, USB Power"),
                        Map.entry("Total Output Power", "24 W RMS"),
                        Map.entry("Frequency Response", "60 Hz – 20 kHz"),
                        Map.entry("Driver Size", "4 in Woofer, 1 in Tweeter"),
                        Map.entry("Subwoofer Included", "No"),
                        Map.entry("Bluetooth", "No"),
                        Map.entry("Volume Control", "Front Knob"),
                        Map.entry("Headphone Jack", "Yes"),
                        Map.entry("Microphone Input", "No"),
                        Map.entry("RGB Lighting", "No"),
                        Map.entry("Power Source", "AC Adapter"),
                        Map.entry("Dimensions", "10.2 × 5.4 × 6.7 in (Each Speaker)"),
                        Map.entry("Weight", "7.8 lb"),
                        Map.entry("Color", "Black")
                )
        );

        addProduct(
                "Vertex Studio A300",
                Category.ACCESSORIES,
                Brand.VERTEX,
                "The Vertex Studio A300 delivers powerful stereo audio with enhanced bass and crystal-clear highs. Its angular modern design and front-mounted controls make it an excellent choice for desktop entertainment, gaming, and productivity.",
                new BigDecimal("109.99"),
                10,
                "/images/speakers/speaker 2.png",
                Map.ofEntries(
                        Map.entry("Speaker Type", "2.0 Stereo Desktop Speakers"),
                        Map.entry("Connectivity", "3.5 mm Audio, USB Power"),
                        Map.entry("Total Output Power", "40 W RMS"),
                        Map.entry("Frequency Response", "50 Hz – 20 kHz"),
                        Map.entry("Driver Size", "4.5 in Woofer, 1 in Tweeter"),
                        Map.entry("Subwoofer Included", "No"),
                        Map.entry("Bluetooth", "No"),
                        Map.entry("Volume Control", "Front Knob"),
                        Map.entry("Headphone Jack", "Yes"),
                        Map.entry("Microphone Input", "Yes"),
                        Map.entry("RGB Lighting", "White Accent LED"),
                        Map.entry("Power Source", "AC Adapter"),
                        Map.entry("Dimensions", "11.0 × 5.7 × 7.0 in (Each Speaker)"),
                        Map.entry("Weight", "9.4 lb"),
                        Map.entry("Color", "Black")
                )
        );

        addProduct(
                "Resona SoundBar S500",
                Category.ACCESSORIES,
                Brand.RESONA,
                "The Resona SoundBar S500 delivers room-filling stereo sound in a slim profile that fits perfectly beneath a monitor or television. Tuned for clear dialogue and balanced audio, it's ideal for gaming, streaming, music, and movies.",
                new BigDecimal("149.99"),
                5,
                "/images/speakers/speaker 3.png",
                Map.ofEntries(
                        Map.entry("Speaker Type", "Wired Sound Bar"),
                        Map.entry("Connectivity", "USB, 3.5 mm Audio, Optical"),
                        Map.entry("Total Output Power", "60 W RMS"),
                        Map.entry("Frequency Response", "45 Hz – 20 kHz"),
                        Map.entry("Driver Size", "Dual 2.5 in Full-Range Drivers"),
                        Map.entry("Subwoofer Included", "No"),
                        Map.entry("Bluetooth", "No"),
                        Map.entry("Volume Control", "Front Dial"),
                        Map.entry("Headphone Jack", "No"),
                        Map.entry("Microphone Input", "No"),
                        Map.entry("RGB Lighting", "No"),
                        Map.entry("Power Source", "AC Adapter"),
                        Map.entry("Dimensions", "25.5 × 3.2 × 3.6 in"),
                        Map.entry("Weight", "5.6 lb"),
                        Map.entry("Color", "Black")
                )
        );
    }
}
