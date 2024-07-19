package com.ecommerce.bestNutrition.config;

import java.util.Base64;

public class JwtConstant {
    public static final String SECRET_KEY = Base64.getEncoder().encodeToString("adokhasheiiqjdopskflsdcmxkmlasmlazlakndklasndklsafasllmslaAyy".getBytes());
    public static final String JWT_HEADER = "Authorization";
}
