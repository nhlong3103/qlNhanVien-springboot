package qlnhanvien.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DepartmentTypeConvert implements AttributeConverter<Department.Type, String>{

	@Override
	public String convertToDatabaseColumn(Department.Type attribute) {
		if(attribute==null) {
			return null;
		}
	return attribute.getValue();
	}

	@Override
	public Department.Type convertToEntityAttribute(String dbData) {
		if(dbData==null) {
			return null;
		}
		return Department.Type.toEnum(dbData);
	}

}
