package id.its.akademik.endpoint;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

public abstract class BaseEndpoint {
	
	private final static ObjectMapper objectMapper;
	
	static {
		objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
	}
	
	public Object toJson(Object obj, String filterParam) {
		
		if (filterParam == null || filterParam.equals("")) {
			return toJson(obj);
		}

		String filterName = "";
		
		try {
			if (obj instanceof List) {
				Object o = ((List) obj).get(0);
				filterName = o.getClass().getSimpleName();
			} else {
				filterName = obj.getClass().getSimpleName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringBuffer jsonString = new StringBuffer();
		String[] fields = null;
		if (filterParam != null)
			fields = filterParam.split(",");

		SimpleFilterProvider filters = null;

		Set<String> set = new HashSet<String>();
		for (String field : fields) {
			set.add(field);
		}
		filters = new SimpleFilterProvider().addFilter(filterName, SimpleBeanPropertyFilter.filterOutAllExcept(set))
				.setFailOnUnknownId(false);

		try {

			if (filterParam == null)
				return obj;
			else if (obj instanceof List) {
				List list = (List) obj;

				for (int i = 0; i < list.size(); i++) {
					Object o = list.get(i);

					jsonString.append(objectMapper.writer(filters).writeValueAsString(o));
					if (i != list.size() - 1) {
						jsonString.append(",");
					}
				}

				jsonString.insert(0, "[");
				jsonString.append("]");
			} else {
				jsonString.append(objectMapper.writer(filters).writeValueAsString(obj));
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString.toString();
	}

	public String toJson(Object obj) {

		String filterName = "";
		try {
			if (obj instanceof List) {
				Object o = ((List) obj).get(0);
				filterName = o.getClass().getSimpleName();
			} else {
				filterName = obj.getClass().getSimpleName();
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

		FilterProvider filters = new SimpleFilterProvider()
				.addFilter(filterName, SimpleBeanPropertyFilter.serializeAllExcept(new HashSet<String>()))
				.setFailOnUnknownId(false);

		StringBuffer jsonString = new StringBuffer();
		try {
			if (obj instanceof List) {
				List list = (List) obj;

				for (int i = 0; i < list.size(); i++) {
					Object o = list.get(i);

					jsonString.append(objectMapper.writer(filters).writeValueAsString(o));
					if (i != list.size() - 1) {
						jsonString.append(",");
					}
				}

				jsonString.insert(0, "[");
				jsonString.append("]");
			} else {
				jsonString.append(objectMapper.writer(filters).writeValueAsString(obj));
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString.toString();
	}

}
