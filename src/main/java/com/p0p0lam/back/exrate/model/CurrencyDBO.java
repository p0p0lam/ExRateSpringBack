package com.p0p0lam.back.exrate.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Sergey on 23.02.2016.
 */
@Document(collection = "currencies")
public class CurrencyDBO extends IdNameDBO{
}
