package com.hellokoding.springboot.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	private final NoteRepository noteRepository;
	private final BackupHistoryRepository backupHistory;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	public ScheduledTasks(NoteRepository noteRepository, BackupHistoryRepository backupHistory) {
		// TODO Auto-generated constructor stub
		this.noteRepository = noteRepository;
		this.backupHistory = backupHistory;
	}
	
	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		log.info("The time is now {}", dateFormat.format(new Date()));
		

		List<Note> notes = new ArrayList<Note>();
		noteRepository.findAll().forEach(notes::add);
		ObjectMapper mapper = new ObjectMapper();

        // Java objects to JSON file
		String currentUsersHomeDir = System.getProperty("user.home");
		String otherFolder = currentUsersHomeDir + File.separator + "anjar";
		if(!new File(otherFolder).exists()) {
			new File(otherFolder).mkdir();
		}
        try {
        	String filename = UUID.randomUUID().toString();
        	File file = new File(otherFolder+"/"+filename+".json");
			mapper.writeValue(file, notes);
			
			String zipFileName = otherFolder+"/"+filename+".zip";
			
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
 
            zos.putNextEntry(new ZipEntry(file.getName()));
 
            byte[] bytes = Files.readAllBytes(Paths.get(file.getCanonicalPath()));
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
            zos.close();
            
            file.delete();
            
			BackupHistory b = new BackupHistory();
			b.setFilename(filename+".zip");
			b.setCreatedOn(LocalDateTime.now());
			b.setUpdatedOn(LocalDateTime.now());
			backupHistory.save(b);
			
			SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo("anjares90@gmail.com");

	        msg.setSubject("Backup History");
	        msg.setText("Backup history: "+zipFileName+".zip"+" Time : "+b.getCreatedOn());
	        javaMailSender.send(msg);

	        System.out.println("Mail sended");
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}