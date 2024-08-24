package com.example.SpringUploadDownloadFiles;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FileManagerGuiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void ShouldUploadFileFormIsVisible() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/uploader"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("uploader"))
                .andExpect(model().attribute("files", hasItem("12tri_resistance.jpg")));
    }

    @Test
    void ShouldFileListFormIsVisible() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/files"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("file-list"))
                .andExpect(model().attribute("files", hasItem("12tri_resistance.jpg")));
    }

    @Test
    void ShouldUploadFile() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:" + port + "/uploader");
        driver.findElement(By.xpath("//form[@id='uploadForm']//label[@for='file']")).click();
        WebElement uploadElement = driver.findElement(By.id("file"));
        if (uploadElement.isDisplayed() && uploadElement.isEnabled()) {
            uploadElement.sendKeys(".gitignore");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
        } else {
            System.out.println("Element nie jest widoczny lub nie jest interaktywny.");
        }
        driver.quit();
    }
}