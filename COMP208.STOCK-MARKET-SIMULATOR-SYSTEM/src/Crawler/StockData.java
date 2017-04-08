package Crawler;

public class StockData {
	//snd1l1yr
	private String code; // 股票编码
	private String name; // 股票名称
	private String date; // 交易日期
	private String lastTradeDate;
	private Double lastTradePrice;
	private Double dividentYeild;
	private Double peRatio;
	
	private double open = 0.0; // 开盘价
	private double high = 0.0; // 最高价
	private double low = 0.0; // 最低价
	private double close = 0.0; // 最后一次交易价格，相当于收盘价
	private double volume = 0.0;// 总交易手
	private double adj = 0.0; // 最后一次交易价格 (今天的收盘价当做加权价格)
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLastTrade(){
		return lastTradeDate;
	}
	public void setLastTrade(String lastTradeDate){
		this.lastTradeDate = lastTradeDate;
	}
	public Double getLastPrice(){
		return lastTradePrice;
	}
	public void setLastPrice(double lastTradePrice){
		this.lastTradePrice = lastTradePrice;
	}
	public Double getDivident(){
		return dividentYeild;
	}
	public void setDivident(double dividentYeild){
		this.dividentYeild = dividentYeild;
	}
	public double getPE(){
		return peRatio;
	}
	public void setPE(double peRatio){
		this.peRatio = peRatio;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public double getAdj() {
		return adj;
	}
	public void setAdj(double adj) {
		this.adj = adj;
	}
	
	public String toString(){
		return "code: "+code + "\t" + "date: " + date + "\t" +
				"open: " + open + "\t" + "high: " + high + "\t" + "low: " +low;
	}
}
