package com.example.asm03spring.service;
import com.example.asm03spring.dao.MedicalInformationRepository;
import com.example.asm03spring.entity.MedicalInformation;
import com.example.asm03spring.entity.Patient;
import com.example.asm03spring.entity.User;
import com.example.asm03spring.dto.SendMedicalInformationRequest;
import com.example.asm03spring.dto.SendMedicalInformationResponse;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MedicalInformationService {
    private final MedicalInformationRepository medicalInformationRepository;
    private final JavaMailSender javaMailSender;
    private final UserService userService;
    private final PatientService patientService;

    public void createPDF(String fileName, String content) {
        try {
            PDDocument document = new PDDocument(); // create document
            PDPage page = new PDPage(PDRectangle.A4); // create page a4
            document.addPage(page); // add page a4

            try(PDPageContentStream contentStream = new PDPageContentStream(document,page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                float margin = 50; // Set your margin here
                float yStart = page.getMediaBox().getHeight() - margin;

                String[] lines = content.split("\\n");
                for (String line : lines) {
                    String[] segments = line.split("\\.");
                    for (String segment : segments) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin, yStart);
                        contentStream.showText(segment.trim() + ".");
                        contentStream.endText();
                        yStart -= 15; // Adjust line height as needed
                    }
                }
            }

            document.save(fileName);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPDFByEmail(String patientEmail, String filePath) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setTo(patientEmail);
            helper.setSubject("Medical Information");
            helper.setText("Hello, this is your medical information");
            helper.addAttachment("medical_info.pdf", new File(filePath));
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SendMedicalInformationResponse responseMedicalInformation(SendMedicalInformationRequest request) {
        // find user by email
        User user = userService.findByEmail(request.getEmail());
        int userId = user.getId();

        // get patient instance by user
        Patient patient = patientService.findPatientByPatientId(userId);

        MedicalInformation medicalInformation = new MedicalInformation();
        medicalInformation.setBasicDisease(request.getDisease());
        medicalInformation.setDetailedDescription(request.getDescription());
        medicalInformationRepository.save(medicalInformation);

        patient.setMedicalInformation(medicalInformation);
        patientService.save(patient);


        String content = "Hello, I'm is your doctor." +
                "Basic Disease: " + medicalInformation.getBasicDisease() +
                "\nDetailed Description: " + medicalInformation.getDetailedDescription();

        // get medical information based on patient
        createPDF("medical_info.pdf",content);
        sendPDFByEmail(request.getEmail(),"medical_info.pdf");

        return SendMedicalInformationResponse.builder().message("Send mail successfully!").build();
    }




}
