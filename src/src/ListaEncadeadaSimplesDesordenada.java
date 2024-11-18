package src;

public class ListaEncadeadaSimplesDesordenada <X> implements Cloneable
{
	private class No implements Cloneable
	{
	    private X  info;
	    private No prox;
	    
	    public No (X i, No p)
	    {
	        this.info=i;
	        this.prox=p;
	    }
	    
	    public No (X i)
	    {
	        this.info=i;
	        this.prox=null;
	    }
	    
	    public X getInfo ()
	    {
	        return this.info;
	    }
	    
	    public No getProx ()
	    {
	        return this.prox;
	    }
	    
	    public void setInfo (X i)
	    {
	        this.info=i;
	    }
	    
	    public void setProx (No p)
	    {
	        this.prox=p;
	    }
	    
	    @Override
        public String toString ()
        {
			return this.info.toString();
		}
		
		@Override
		public boolean equals (Object obj)
		{
			if (obj==this) return true;
			if (obj==null) return false;
			if (obj.getClass()!=this.getClass()) return false;
			
			if (!((No)obj).info.equals(this.info)) return false;
			
			return true;
		}
		
		@Override
		public int hashCode ()
		{
			int ret=1;
			
			ret=2*ret+this.info.hashCode();
		
		    if (ret<0) ret=-ret;
			
			return ret;
		}
		
		public No (No modelo) throws Exception
		{
			if (modelo==null) throw new Exception ("Modelo ausente");
			
			if (modelo.info instanceof Cloneable)
				this.info = new Clonador<X> ().clone (modelo.info);
			else
			    this.info = modelo.info;
		}
		
		public Object clone ()
		{
			No ret=null;
			
			try
			{
				ret = new No (this);
		    }
			catch (Exception erro)
			{} // sei que nao vai dar erro, pois o construtor de cópia só da erro ao receber parâmetro null, e o que estou passando como parâmetro para ele é o this, que NUNCA, NUNQUINHA MESMO, é null

			return ret;
		}
	}
	
	private No primeiro;
	
	public ListaEncadeadaSimplesDesordenada(){
		this.primeiro = null;
	}
	
	public void guardeNoInicio (X i) throws Exception
	{
		if (i==null) throw new Exception ("Informação ausente");
		
		this.primeiro = new No (i,this.primeiro);
	}
	
	public void guardeNoFinal (X i) throws Exception
	{
		if (i==null) throw new Exception ("Informação ausente");

        if (this.primeiro==null) this.primeiro = new No (i);
        
        No atual=this.primeiro;
        
        while (atual.getProx()!=null)
            atual=atual.getProx();
        
        atual.setProx(new No (i));
	}
	
	/*
        Digamos que a lista tenha n nós; incluir na posicao 0,
        é incluir no inicio; incluir na posicao 1, é incluir
        entre o 1º e o 2º nó; e assim por diante até que,
        incluir na posicao n, é incluir no final; MAS NOTE,
        incluir numa posicao>n leva a uma exceção.
    */
	public void guardeEm (int posicao, X i) throws Exception
	{
		if (posicao<0) throw new Exception ("Posicao invalida");
		if (i==null) throw new Exception ("Falta o que inserir");
		
		X inf = new Clonador<X>().clone(i);
		
		if (posicao==0)
		{
			this.primeiro = new No (inf,this.primeiro);
			return;
		}
		
		No  atual   =this.primeiro;
		int posAtual=0;
		
		while (atual.getProx()!=null && posAtual<posicao-1)
		{
			atual=atual.getProx();
			posAtual++;
		}
		
		atual.setProx (new No (inf,atual.getProx()));
	}
	
	public boolean tem (X i)
	{
		No atual=this.primeiro;
		
		while (atual!=null)
		{
			if (atual.getInfo().equals(i)) return true;
			
			atual=atual.getProx();
		}
		
		return false;
	}
	
	public X getPrimeiro () throws Exception
	{
		if(this.primeiro==null){
			throw new Exception ("ponteiro nulo");
		}
		
		X infoPrimeiro = new Clonador<X>().clone(this.primeiro.getInfo());
		return infoPrimeiro;
	}
	
	public X getUltimo () throws Exception
	{
		if (this.primeiro==null) throw new Exception("A lista está vazia");
		 
		No atual = this.primeiro;
		while(atual.getProx()!=null){
			atual= atual.getProx();
		}
		X infoUltimo = new Clonador<X>().clone(atual.getInfo());
		return infoUltimo;
			
	}
	
	int getTamanho()
	{
			if(this.primeiro == null)
			{
				return 0;
			}
			
			No atual = this.primeiro;
			int elementos = 0;
			
			while(atual != null)
			{
					atual = atual.getProx();
					elementos++;
			}
			
			return elementos;
	}
	
	// posicao poderá ser 0, 1, etc
	public X get (int posicao) throws Exception
	{
		if(this.primeiro == null)
		{
			throw new Exception("Nó invalido");
		}
		if(posicao < 0)
		{
			throw new Exception("Posicao invalida");
		}
		if(posicao > getTamanho() - 1)
		{
				throw new Exception("Posicao invalida");	
		}
		if(posicao == 0)
		{
				return this.primeiro.getInfo();
		}
		
		No atual = this.primeiro;
		int indice = 0;
		
		while(atual != null && indice<posicao)
		{
				atual = atual.getProx();
				indice++;
		}
		
		X cont = new Clonador<X>().clone(atual.getInfo());
		return cont;
	}
	
	public void removaPrimeiro () throws Exception
	{
		if (this.primeiro==null) throw new Exception ("Nó invalido");
		this.primeiro = this.primeiro.getProx();
	}
	
	public void removaUltimo () throws Exception
	{
		if (this.primeiro==null) throw new Exception ("Nó invalido");
		
		if (this.primeiro.getProx()==null)
		{
			this.primeiro=null;
			return;
		}
		
		No atual = this.primeiro;
		
		while (atual.getProx() != null){
			atual = atual.getProx();
		}

		atual.setProx(null);
	}
	
	// posições serão numeradas 0, 1, 2, etc
	public void remova (int posicao) throws Exception
	{
		if(posicao < 0 || posicao >= this.getTamanho()){
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
			
			
		if(posicao == 0){
			this.primeiro = this.primeiro.getProx();
		} else{
			No aux = this.primeiro;
			for(int i = 0; i < posicao - 1; i++){
			    aux = aux.getProx();
			}
		
			aux.setProx(aux.getProx().getProx()); 
		}
	}
	
    @Override
    public String toString ()
    {
		if (this.primeiro==null) return "[]";
		
		String ret="[";
		
		ret+=this.primeiro.getInfo();
		
		No atual = this.primeiro.getProx();
		
		while (atual!=null)
		{
			ret+=", "+atual.getInfo();
			atual=atual.getProx();
		}
		
		return ret+"]";
	}
	
	@Override
	public boolean equals (Object obj)
	{
		if (obj==this) return true;
		if (obj==null) return false;
		if (obj.getClass()!=this.getClass()) return false;
		
		No atualDoThis = this.primeiro;
		No atualDoObj  = ((ListaEncadeadaSimplesDesordenada<X>)obj).primeiro;
		
		while (atualDoThis!=null && atualDoObj!=null)
		{
			if (!atualDoThis.getInfo().equals(atualDoObj.getInfo())){
				 
				return false;
			}
            atualDoThis=atualDoThis.getProx();
            atualDoObj =atualDoObj .getProx();
		}
		
		if (atualDoThis!=null || atualDoObj!=null) return false;
		
		return true;
	}
	
	@Override
	public int hashCode ()
	{
		int ret=1;
		
		No atual=this.primeiro;
		
		while (atual!=null)
		{
			ret = ret*2 + atual.getInfo().hashCode();
			atual=atual.getProx();
		}		
		
		if (ret<0) ret=-ret;
		return ret;
	}
	
	public ListaEncadeadaSimplesDesordenada (ListaEncadeadaSimplesDesordenada<X> modelo) throws Exception
	{
		if (modelo==null) throw new Exception ("Modelo ausente");
		
		if (modelo.primeiro==null)
		{
			this.primeiro=null;
			return;
		}
		
		this.primeiro = new No (modelo.primeiro.getInfo());
		
		No atualDoThis   = this.primeiro;
		No atualDoModelo = modelo.primeiro.getProx();
		
		while (atualDoModelo!=null)
		{
			atualDoThis.setProx (new No (atualDoModelo.getInfo()));
			
			atualDoThis  =atualDoThis  .getProx();
			atualDoModelo=atualDoModelo.getProx();
		}
	}
	
	public Object clone ()
	{
		ListaEncadeadaSimplesDesordenada<X> ret=null;
		
		try
		{
			ret = new ListaEncadeadaSimplesDesordenada (this);
		}
		catch (Exception erro)
		{} // sei que nao vai dar erro, pois o construtor de cópia só da erro ao receber parâmetro null, e o que estou passando como parâmetro para ele é o this, que NUNCA, NUNQUINHA MESMO, é null

		return ret;
	}
}
