package com.datagen.backend.sql;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;

import com.datagen.backend.model.JsNode;
import com.datagen.backend.model.Schema;
import com.datagen.backend.sql.helper.TypeChecker;
import com.google.common.collect.Multimap;

public class SqlSyntaxBuilder {

	public StringBuilder tableBuilder(List<Schema> schema,Multimap<Integer, String> columnName){
		StringBuilder tsb = new StringBuilder();
		//String id_identifier = RandomStringUtils.random(8, true, true);
		LinkedHashSet<Integer> parent = new LinkedHashSet<Integer>();
		for(Schema s:schema) {
			parent.add(s.getKey());
		}
		for(Schema s:schema) {
			int key = s.getKey();
			Collection<JsNode> values = s.getValue();
			String pn = getParentName(values);
			tsb.append("CREATE TABLE IF NOT EXISTS"+" "+pn+key);
			tsb.append("(");
			columnBuilder(tsb,values,key,parent,columnName);

			tsb.append(pn+key+" "+"INTEGER"+" "+"NOT NULL"+","+" ");
			tsb.append("PRIMARY KEY"+" "+"("+pn+key+")");
			columnName.put(key,pn+key);
			
			tsb.append(")");
			tsb.append(";");
			tsb.append("\n");
		}
		return tsb;
	}

	public void columnBuilder( StringBuilder tsb, Collection<JsNode> values, Integer key, LinkedHashSet<Integer> parent,Multimap<Integer, String> columnName){
		int ki =key;
		for(JsNode value : values) {
			int id = value.getId();
			String name = value.getNodeName();
			String type = value.getValueType();
			LinkedMultiValueMap<String, Object> valueMap = value.getValueMap();
			if(parent.contains(id)){
				key=id;
				tsb.append(name+key+" "+"INTEGER"+" "+"NOT NULL"+","+" ");
				tsb.append("FOREIGN KEY"+" "+"("+name+key+")"+" "+"REFERENCES"+" "+name+key+"("+name+id+")"+","+" ");
				columnName.put(ki,name+key);
			}else{
				String vType = TypeChecker.typeCaster(type,valueMap);
				tsb.append(name+" "+vType.toUpperCase()+","+" ");
				columnName.put(ki,name);
			}
		}
	}

	public StringBuilder rowBuilder(List<Schema> schema, Multimap<Integer, Object> childVal, Map<Integer, Collection<String>> column){
		StringBuilder vsb = new StringBuilder();

		for(Schema s:schema) {
			int key = s.getKey();
			Collection<String> cName = column.get(key);
			Collection<Object> values = childVal.get(key);
			Collection<JsNode> vals = s.getValue();
			String pn = getParentName(vals);
			int cNameSize = cName.size();
			int valueSize = values.size();

			String parName= pn+key;
			vsb.append("INSERT INTO"+" "+pn+key+" ");
			vsb.append("(");
			int cname_counter=1;
			for(String cn : cName) {
				if(cname_counter==cNameSize){
					vsb.append(cn);
				}else{
					vsb.append(cn+",");
				}
				cname_counter++;
			}

			vsb.append(")");
			vsb.append(" ");
			vsb.append("VALUES"+" ");
			vsb.append("(");

			int value_counter =1;
			for(Object value : values) {
				if(value_counter==valueSize){
					vsb.append(value);
				}else{
					vsb.append(value+",");
				}
				value_counter++;
			}
			vsb.append(")");
			vsb.append(";");
			vsb.append("\n");
		}
		return vsb;
	}

	public String getParentName(Collection<JsNode> values){
		String pName = null;
		for(JsNode value : values) {
			pName = value.getParentName();
		}
		return pName;
	}
	
	
	
}
