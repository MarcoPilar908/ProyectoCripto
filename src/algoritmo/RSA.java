package algoritmo;
import java.math.*;
import java.util.*;
public class RSA {
    //variables
    int tamanioPrimo;
    private BigInteger numP, numQ, numN;
    private BigInteger numPhi;
    private BigInteger numE, numD;
    public RSA(int tamPrimo){
        tamanioPrimo = tamPrimo;
        generarPrimos();
    }
    public RSA(BigInteger P, BigInteger Q,int tamPrimo){
        tamanioPrimo = tamPrimo;
        numP=P;
        numQ=Q;
        generarClaves();
    }
    public void generarPrimos(){
    numP=new BigInteger(tamanioPrimo,10,new Random());
    do{
    numQ=new BigInteger(tamanioPrimo,10, new Random());
    }while(numQ.compareTo(numP)==0);
    }
    
    public void generarClaves(){
    numN= numP.multiply(numQ);
    //(nump -1)*(numQ-1)
    numPhi= numP.subtract(BigInteger.valueOf(1));
    numPhi= numPhi.multiply(numQ.subtract(BigInteger.valueOf(1)));
    do{
        numE=new BigInteger (2*tamanioPrimo,new Random());
    }while(numE.compareTo(numPhi) != -1 || numE.gcd(numPhi).compareTo(BigInteger.valueOf(1)) != 0);//para calcular el maximo comun divisor entre e y phi
    numD= numE.modInverse(numPhi);
    }
    
    public BigInteger[] Encriptar (String mensaje){
    int i;
    byte[] temp = new byte[1];
    byte[] digitos = mensaje.getBytes();
    BigInteger[] bigDigitos = new BigInteger[digitos.length];
    
    for(i=0; i<bigDigitos.length;i++){
    temp[0]=digitos[i];
    bigDigitos[i]=new BigInteger(temp);
    }
    BigInteger[] encriptado = new BigInteger[bigDigitos.length];
    
    for(i=0; i<bigDigitos.length; i++){
    encriptado[i] = bigDigitos[i].modPow(numE, numN);
    }
        return (encriptado);
    }
    
    public String Desencriptar(BigInteger[] encriptado){
        BigInteger[] desencriptado = new BigInteger[encriptado.length];
        
        for(int i=0; i<desencriptado.length; i++){
            desencriptado[i]=encriptado[i].modPow(numD, numN);
        }
        char[] arregloChar = new char[desencriptado.length];
        
        for(int i=0; i<arregloChar.length; i++){
        arregloChar[i]= (char) (desencriptado[i].intValue());
        }
        
        return (new String(arregloChar));
    }

    public BigInteger getNumP() {
        return numP;
    }

    public BigInteger getNumN() {
        return numN;
    }

    public BigInteger getNumPhi() {
        return numPhi;
    }

    public BigInteger getNumE() {
        return numE;
    }

    public BigInteger getNumD() {
        return numD;
    }

    public BigInteger getNumQ() {
        return numQ;
    }
}
