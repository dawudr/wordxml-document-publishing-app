package com.pearson.btec.validator;

import com.pearson.btec.form.FileUploadForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FileUploadValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		//just validate the FileUpload instances
		return FileUploadForm.class.isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {
		
		FileUploadForm file = (FileUploadForm)target;
		
		if(file.getFiles().size()==0){
			errors.rejectValue("file", "required.fileUpload");
		}
	}
	
}