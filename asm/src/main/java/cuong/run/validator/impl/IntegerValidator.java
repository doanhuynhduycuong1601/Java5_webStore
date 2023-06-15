package cuong.run.validator.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntegerValidator implements ConstraintValidator<cuong.run.validator.Integer, String> {
	private int min;
	private int max;
	private String name;
	@Override
    public void initialize(cuong.run.validator.Integer p) {
        this.min = p.min();
        this.max = p.max();
        this.name = p.name();
    }
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		String msg = "";
		if (value.equals("")) {
			msg = " không được để trống";
			set(context, this.name + msg);
            return false;
        }
		
		double doubles = 0;
		try {
			doubles = Double.parseDouble(value);
		}catch(NumberFormatException exp) {
			msg = " phải là số";
			set(context, this.name + msg);
			return false;
		}
		
		int ints = 0;
		try {
			ints = Integer.parseInt(value);
		}catch(NumberFormatException exp) {
			msg = " phải là số nguyên";
			set(context, this.name + msg);
			return false;
		}
		
		if(ints < min) {
			msg = " bạn nhập phải lớn hơn số " + min;
			set(context, this.name + msg);
			return false;
		}
		
		if(ints > max) {
			msg = " bạn nhập phải nhỏ hơn số " + max;
			set(context, this.name + msg);
			return false;
		}
		
		return true;
	}
	
	
	private void set(ConstraintValidatorContext context, String msg) {
		context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
	}

}
