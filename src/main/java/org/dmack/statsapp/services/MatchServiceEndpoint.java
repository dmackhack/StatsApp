package org.dmack.statsapp.services;

import java.util.Date;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.dmack.statsapp.domain.City;
import org.dmack.statsapp.domain.Match;
import org.dmack.statsapp.domain.USAddress;
import org.springframework.beans.factory.annotation.Autowired;

//serviceName = "Calculator"
//portName = "CalculatorPort",
//endpointInterface = "org.apache.geronimo.samples.jws.Calculator",
//targetNamespace = "http://jws.samples.geronimo.apache.org",
//wsdlLocation = "WEB-INF/wsdl/CalculatorService.wsdl"
//@Service(value = "matchServiceEndpoint")
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class MatchServiceEndpoint implements MatchService
{
    @Autowired
    private MatchService matchService;

    public int add(int var1, int var2)
    {
        return matchService.add(var1, var2);
    }

    public Match createMatch(@WebParam(name = "date") Date date)
    {
        return matchService.createMatch(date);
    }

    public USAddress createAddress(String anAddress)
    {
        return matchService.createAddress(anAddress);
    }

    public City getCity()
    {
        return matchService.getCity();
    }

    public Match getMatch(Long id)
    {
        return matchService.getMatch(id);
    }

}
