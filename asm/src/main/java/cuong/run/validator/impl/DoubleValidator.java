package cuong.run.validator.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DoubleValidator implements ConstraintValidator<cuong.run.validator.Double, String> {
	private double min;
	private double max;
	private String name;
	@Override
    public void initialize(cuong.run.validator.Double p) {
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
		
		if(doubles < min) {
			msg = " bạn nhập phải lớn hơn số " + doubles;
			set(context, this.name + msg);
			return false;
		}
		
		if(doubles > max) {
			msg = " bạn nhập phải nhỏ hơn số " + doubles;
			set(context, this.name + msg);
			return false;
		}
		
		return true;
	}
	
	
	private void set(ConstraintValidatorContext context, String msg) {
		context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
	}

}
