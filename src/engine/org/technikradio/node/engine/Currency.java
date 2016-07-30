/*
Copyright (c) 2016, Technikradio
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of Node2 nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.technikradio.node.engine;

/**
 * This enumeration contains different constants for enumerations. There is no
 * warranty for completeness or correctness. Please do a pull request if you
 * find mistakes.
 * 
 * @author doralitze
 * @deprecated since I noticed that it's possible to get an instance of a
 *             currency from the ISO 4217 code.
 *
 */
@Deprecated
public enum Currency {
	/**
	 * The Afghan afghani (currency of Afghanistan)
	 */
	AFN,
	/**
	 * The currency of the euro zone
	 */
	EUR,
	/**
	 * The currency of Albania
	 */
	ALL,
	/**
	 * The Algerian dinar (currency of Algeria)
	 */
	DZD,
	/**
	 * The US Dollar
	 */
	USD,
	/**
	 * The Angolan kwanza
	 */
	AOA,
	/**
	 * The east Caribbean dollar
	 */
	XCD,
	/**
	 * The Argentine peso
	 */
	ARS,
	/**
	 * The Armenian dram
	 */
	AMD,
	/**
	 * Aruban florin
	 */
	AWG,
	/**
	 * The Australian Dollar
	 */
	AUD,
	/**
	 * The Azerbaijani manat
	 */
	AZN,
	/**
	 * The Bahamian dollar
	 */
	BSD,
	/**
	 * The Bahraini dinar
	 */
	BHD,
	/**
	 * The Bangladeshi taka
	 */
	BDT,
	/**
	 * The Barbadian dollar
	 */
	BBD,
	/**
	 * The Belarusian ruble
	 */
	BYR,
	/**
	 * The Belize dollar
	 */
	BZD,
	/**
	 * West African CFA franc
	 */
	XOF,
	/**
	 * The Bermudian dollar
	 */
	BMD,
	/**
	 * The Bhutanese ngultrum
	 */
	BTN,
	/**
	 * The Bolivian boliviano
	 */
	BOB,
	/**
	 * The Bosnia and Herzegovina convertible mark
	 */
	BAM,
	/**
	 * The Botswana pula
	 */
	BWP,
	/**
	 * The Brazilian real
	 */
	BRL,
	/**
	 * The Brunei dollar
	 */
	BND,
	/**
	 * The Bulgarian lev
	 */
	BGN,
	/**
	 * The Burundi franc
	 */
	BIF,
	/**
	 * The Cape Verdean escudo
	 */
	CVE,
	/**
	 * The Cambodian riel
	 */
	KHR,
	/**
	 * Central African CFA franc
	 */
	XAF,
	/**
	 * The Canadian dollar
	 */
	CAD,
	/**
	 * The Cayman Islands dollar
	 */
	KYD,
	/**
	 * The Chilean peso
	 */
	CLP,
	/**
	 * The Chinese Yuan Renminbi
	 */
	CNY,
	/**
	 * The Colombian peso
	 */
	COP,
	/**
	 * The Comorian franc
	 */
	KMF,
	/**
	 * The Congolese franc
	 */
	CDF,
	/**
	 * The Costa Rican colon
	 */
	CRC,
	/**
	 * The Croatian kuna
	 */
	HRK,
	/**
	 * The Cuban peso
	 */
	CUP,
	/**
	 * The Netherlands Antillean guilder
	 */
	ANG,
	/**
	 * The Czech koruna
	 */
	CZK,
	/**
	 * The Danish krone
	 */
	DKK,
	/**
	 * The Djiboutian franc
	 */
	DJF,
	/**
	 * The Dominican peso
	 */
	DOP,
	/**
	 * The Egyptian pound
	 */
	EGP,
	/**
	 * The Eritrean nakfa
	 */
	ERN,
	/**
	 * The Ethiopian birr
	 */
	ETB,
	/**
	 * The Falkland Islands pound
	 */
	FKP,
	/**
	 * The Fijian dollar
	 */
	FJD,
	/**
	 * The CFP franc
	 */
	XPF,
	/**
	 * The Gambian dalasi
	 */
	GMD,
	/**
	 * The Georgian lari
	 */
	GEL,
	/**
	 * The Ghanaian cedi
	 */
	GHS,
	/**
	 * The Gibraltar pound
	 */
	GIP,
	/**
	 * The Guatemalan quetzal
	 */
	GTQ,
	/**
	 * The Guernsey Pound
	 */
	GGP,
	/**
	 * The Guinean franc
	 */
	GNF,
	/**
	 * The Guyanese dollar
	 */
	GYD,
	/**
	 * The Haitian gourde
	 */
	HTG,
	/**
	 * The Honduran lempira
	 */
	HNL,
	/**
	 * The Hong Kong dollar
	 */
	HKD,
	/**
	 * The Hungarian forint
	 */
	HUF,
	/**
	 * The Icelandic krona
	 */
	ISK,
	/**
	 * The Indian rupee
	 */
	INR,
	/**
	 * The Indonesian rupiah
	 */
	IDR,
	/**
	 * The SDR (Special Drawing Right) (The currency of the IMF)
	 */
	XDR,
	/**
	 * The Iranian rial
	 */
	IRR,
	/**
	 * The Iraqi dinar
	 */
	IQD,
	/**
	 * The Manx pound
	 */
	IMP,
	/**
	 * The Israeli new sheqel
	 */
	ILS,
	/**
	 * The Jamaican dollar
	 */
	JMD,
	/**
	 * The Japanese yen
	 */
	JPY,
	/**
	 * The Jersey pound
	 */
	JEP,
	/**
	 * The Jordanian dinar
	 */
	JOD,
	/**
	 * The Kazakhstani tenge
	 */
	KZT,
	/**
	 * The Kenyan shilling
	 */
	KES,
	/**
	 * The Kuwaiti dinar
	 */
	KWD,
	/**
	 * The Kyrgyzstani som
	 */
	KGS,
	/**
	 * The Lao kip
	 */
	LAK,
	/**
	 * The Lebanese pound
	 */
	LBP,
	/**
	 * The Lesotho loti
	 */
	LSL,
	/**
	 * The Liberian dollar
	 */
	LRD,
	/**
	 * The Libyan dinar
	 */
	LYD,
	/**
	 * The Swiss franc
	 */
	CHF,
	/**
	 * The Macanese pataca
	 */
	MOP,
	/**
	 * The Macedonian denar
	 */
	MKD,
	/**
	 * The Malagasy ariary
	 */
	MGA,
	/**
	 * The Malawian kwacha
	 */
	MWK,
	/**
	 * The Malaysian ringgit
	 */
	MYR,
	/**
	 * The Maldivian rufiyaa
	 */
	MVR,
	/**
	 * The Mauritanian ouguiya
	 */
	MRO,
	/**
	 * The Mauritian rupee
	 */
	MUR,
	/**
	 * The Mexican peso
	 */
	MXN,
	/**
	 * The Moldovan leu
	 */
	MDL,
	/**
	 * The Mongolian tugrik
	 */
	MNT,
	/**
	 * The Moroccan dirham
	 */
	MAD,
	/**
	 * The Mozambican metical
	 */
	MZN,
	/**
	 * The Myanmar kyat
	 */
	MMK,
	/**
	 * The Namibian dollar
	 */
	NAD,
	/**
	 * The Nepalese rupee
	 */
	NPR,
	/**
	 * The New Zealand dollar
	 */
	NZD,
	/**
	 * The Nicaraguan cordoba
	 */
	NIO,
	/**
	 * The Nigerian naira
	 */
	NGN,
	/**
	 * The North Korean won
	 */
	KPW,
	/**
	 * The Norwegian krone
	 */
	NOK,
	/**
	 * The Omani rial
	 */
	OMR,
	/**
	 * The Pakistani rupee
	 */
	PKR,
	/**
	 * The Papua New Guinean kina
	 */
	PKG,
	/**
	 * The Paraguayan guarani
	 */
	PYG,
	/**
	 * The Peruvian nuevo sol
	 */
	PEN,
	/**
	 * The Philippine peso
	 */
	PHP,
	/**
	 * The Polish zloty
	 */
	PLN,
	/**
	 * The Qatari riyal
	 */
	QAR,
	/**
	 * The Romanian leu
	 */
	RON,
	/**
	 * The Russian ruble
	 */
	RUB,
	/**
	 * The Rwandan franc
	 */
	RWF,
	/**
	 * The Saint Helena pound
	 */
	SHP,
	/**
	 * The Samoan tala
	 */
	WST,
	/**
	 * The Sao Tome and Principe dobra
	 */
	STD,
	/**
	 * The Saudi riyal
	 */
	SAR,
	/**
	 * The Serbian dinar
	 */
	RSD,
	/**
	 * The Seychellois rupee
	 */
	SCR,
	/**
	 * The Sierra Leonean leone
	 */
	SLL,
	/**
	 * The Singapore dollar
	 */
	SGD,
	/**
	 * The Solomon Islands dollar
	 */
	SBD,
	/**
	 * The Somali shilling
	 */
	SOS,
	/**
	 * The South African rand
	 */
	ZAR,
	/**
	 * The South Korean won
	 */
	KRW,
	/**
	 * The South Sudanese pound
	 */
	SSP,
	/**
	 * The Sri Lankan rupee
	 */
	LKR,
	/**
	 * The Sudanese pound
	 */
	SDG,
	/**
	 * The Surinamese dollar
	 */
	SRD,
	/**
	 * The Swazi lilangeni
	 */
	SZL,
	/**
	 * The Swedish krona
	 */
	SEK,
	/**
	 * The Syrian pound
	 */
	SYP,
	/**
	 * The New Taiwan dollar
	 */
	TWD,
	/**
	 * The Tajikistani somoni
	 */
	TJS,
	/**
	 * The Tanzanian shilling
	 */
	TZS,
	/**
	 * The Thai baht
	 */
	THB,
	/**
	 * The Tongan paʻanga
	 */
	TOP,
	/**
	 * The Trinidad and Tobago dollar
	 */
	TTD,
	/**
	 * The Tunisian dinar
	 */
	TND,
	/**
	 * The Turkish lira
	 */
	TRY,
	/**
	 * The Turkmenistani manat
	 */
	TMT,
	/**
	 * The Ugandan shilling
	 */
	UGX,
	/**
	 * The Ukrainian hryvnia
	 */
	UAH,
	/**
	 * The UAE dirham (The currency of the United Arab Emirates)
	 */
	AED,
	/**
	 * The Pound sterling
	 */
	GBP,
	/**
	 * The Uruguayan peso
	 */
	UYU,
	/**
	 * The Uzbekistani som
	 */
	UZS,
	/**
	 * The Vanuatu vatu
	 */
	VUV,
	/**
	 * The Venezuelan bolivar
	 */
	VEF,
	/**
	 * The Vietnamese dong
	 */
	VND,
	/**
	 * The Yemeni rial
	 */
	YER,
	/**
	 * The Zambian kwacha
	 */
	ZMW,
}
