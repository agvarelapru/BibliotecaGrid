package com.cdpjosecabrera.utilidades;

public class Utilidades {
	
	
public static boolean compruebaIsbn(String isbn){
		
	boolean correcto = false;
	if(isbn.length()==17 & isbn.contains("-")){
		
		String[] campos;
		campos= isbn.split("-");
		isbn= campos[0]+campos[1]+campos[2]+campos[3];
		int DC = Integer.parseInt(campos[4]);
		boolean impar = true;
		int acu = 0, num =0;
		for(int x=0;x<isbn.length();x++){
			
			if(impar){
				num=Integer.parseInt(Character.toString(isbn.charAt(x)));
				num=num*1;
				acu+=num;
			}else{
				num=Integer.parseInt(Character.toString(isbn.charAt(x)));
				num=num*3;
				acu+=num;						
			}
			impar=!impar;
		}
		int dc=10-(acu%10);
		if(dc==10)dc=0;
		if(dc==DC)correcto=true;
		}
		//System.out.println(acu+"---"+correcto);
		return correcto;
	}


}
