package com.allencam.service;

import com.allencam.dto.TestimonialDTO;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TestimonialGoogleSheetsService {

    private static final String APPLICATION_NAME = "Dental Portfolio Backend";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private Sheets getSheetsClient() throws IOException, GeneralSecurityException {
        // Load the service account credentials from resources
        InputStream credentialsStream = new ClassPathResource("credentials.json").getInputStream();
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY));

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName(APPLICATION_NAME).build();
    }

    public List<TestimonialDTO> getApprovedTestimonials(String spreadsheetId) throws IOException, GeneralSecurityException {
        Sheets sheets = getSheetsClient();

        // Target sheet name and cell range (adjust "Sheet1" or column letters to match your sheet)
        String range = "Sheet1!A2:E"; // Assuming row 1 is the header

        ValueRange response = sheets.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> rows = response.getValues();
        List<TestimonialDTO> results = new ArrayList<>();

        if (rows == null || rows.isEmpty()) {
            return results;
        }

        for (List<Object> row : rows) {
            // Safe index checking to avoid IndexOutOfBoundsException on empty cells
            String timestamp = getCell(row, 0);
            String name = getCell(row, 1);
            String testimonial = getCell(row, 2);
            String rating = getCell(row, 3);
            String approved = getCell(row, 4);

            // Filter on the backend: only include rows where Approved is TRUE
            if ("TRUE".equalsIgnoreCase(approved)) {
                results.add(new TestimonialDTO(timestamp, name, testimonial, rating));
            }
        }

        return results;
    }

    private String getCell(List<Object> row, int index) {
        return (index < row.size() && row.get(index) != null) ? row.get(index).toString().trim() : "";
    }
}