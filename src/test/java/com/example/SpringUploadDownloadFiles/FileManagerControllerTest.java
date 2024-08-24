package com.example.SpringUploadDownloadFiles;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.file.FileSystemException;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FileManagerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private FileManagerService fileManagerService;

    @InjectMocks
    private FileManagerController fileManagerController;

    @Test
    void shouldUploadFile() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "12tri_resistance.jpg", "image/jpeg", "file".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(mockFile))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void shouldReturnInternalServerErrorWhenIOExceptionOccurs() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testfile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "This is the file content".getBytes()
        );

        doThrow(new IOException("Błąd zapisu pliku")).when(fileManagerService).saveFile(mockFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(mockFile))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("false"));
    }

    @Test
    void shouldReturnBadRequestWhenNullFileOccur() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // oczekiwany status HTTP 400
    }

    @Test
    void ShouldReturnBadRequestWhenFileIsEmpty() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testfile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "This is the file content".getBytes()
        );

        doThrow(new NullPointerException("File is empty")).when(fileManagerService).saveFile(mockFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(mockFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));// oczekiwany status HTTP 400
    }

    @Test
    void shouldReturnBadRequestWhenSecurityExceptionOccurs() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testfile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "This is the file content".getBytes()
        );

        doThrow(new SecurityException("Security error")).when(fileManagerService).saveFile(mockFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(mockFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));
    }

    @Test
    void shouldReturnBadRequestWhenFileSystemExceptionOccur() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testfile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "This is the file content".getBytes()
        );

        doThrow(new FileSystemException("C:\\Install\\12tri_resistance.jpg: Proces nie może uzyskać dostępu do pliku, ponieważ jest on używany przez inny proces")).when(fileManagerService).saveFile(mockFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(mockFile))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));// oczekiwany status HTTP 400
    }
}