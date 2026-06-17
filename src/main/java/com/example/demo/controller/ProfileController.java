package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.service.BarcodeService;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.PdfService;
import com.example.demo.service.ProfileService;
import com.example.demo.service.QrCodeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final FileStorageService fileStorageService;
    private final PdfService pdfService;
    private final QrCodeService qrCodeService;
    private final BarcodeService barcodeService;

    public ProfileController(
            ProfileService profileService,
            FileStorageService fileStorageService,
            PdfService pdfService,
            QrCodeService qrCodeService,
            BarcodeService barcodeService) {

        this.profileService = profileService;
        this.fileStorageService = fileStorageService;
        this.pdfService = pdfService;
        this.qrCodeService = qrCodeService;
        this.barcodeService = barcodeService;
    }

    // =========================
    // CRUD OPERATIONS
    // =========================

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @PostMapping
    public Profile createProfile(@RequestBody Profile profile) {
        return profileService.createProfile(profile);
    }

    @GetMapping("/{id}")
    public Profile getProfile(@PathVariable Long id) {
        return profileService.getProfile(id);
    }

    @PutMapping("/{id}")
    public Profile updateProfile(
            @PathVariable Long id,
            @RequestBody Profile profile) {

        return profileService.updateProfile(id, profile);
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
    }

    // =========================
    // PHOTO UPLOAD
    // =========================

    @PostMapping("/{id}/upload-photo")
    public String uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        Profile profile = profileService.getProfile(id);
        String fileName = fileStorageService.saveFile(file);
        profile.setPhotoFileName(fileName);
        profileService.updateProfile(id, profile);
        return "Photo uploaded successfully";
    }

    // =========================
    // PDF EXPORT
    // =========================

    @GetMapping("/{id}/pdf")
    public String generatePdf(@PathVariable Long id) {

        Profile profile = profileService.getProfile(id);

        pdfService.generatePdf(profile);

        return "PDF generated successfully";
    }

    // =========================
    // QR CODE
    // =========================

    @GetMapping("/{id}/qrcode")
    public String generateQrCode(@PathVariable Long id) {

        Profile profile = profileService.getProfile(id);

        qrCodeService.generateQrCode(
                profile.getRegistrationNumber());

        return "QR Code generated successfully";
    }

    // =========================
    // BARCODE
    // =========================

    @GetMapping("/{id}/barcode")
    public String generateBarcode(@PathVariable Long id) {

        Profile profile = profileService.getProfile(id);

        barcodeService.generateBarcode(
                profile.getRegistrationNumber());

        return "Barcode generated successfully";
    }

    // =========================
    // BATCH CREATION
    // =========================

    @PostMapping("/batch")
    public List<Profile> createBatchProfiles(
            @RequestBody List<Profile> profiles) {

        return profiles.stream()
                .map(profileService::createProfile)
                .toList();
    }
}