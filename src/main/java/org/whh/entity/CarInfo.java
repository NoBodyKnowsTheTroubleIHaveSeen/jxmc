package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "car_info")
public class CarInfo extends EntityBase
{
	private String carType;
	private String carName;
	private Double minPrice;
	private Double maxPrice;
	public String getCarType()
	{
		return carType;
	}
	public void setCarType(String carType)
	{
		this.carType = carType;
	}
	public String getCarName()
	{
		return carName;
	}
	public void setCarName(String carName)
	{
		this.carName = carName;
	}
	public Double getMinPrice()
	{
		return minPrice;
	}
	public void setMinPrice(Double minPrice)
	{
		this.minPrice = minPrice;
	}
	public Double getMaxPrice()
	{
		return maxPrice;
	}
	public void setMaxPrice(Double maxPrice)
	{
		this.maxPrice = maxPrice;
	}
	
	
}
