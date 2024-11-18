package src;

public class Clonador<X> {
    public X clone(X x) {
        if (x instanceof Cloneable) {
            try {
                return (X) ((Cloneable) x).getClass().getMethod("clone").invoke(x);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return x; // Se o objeto não for Cloneable, retorne o próprio objeto
        }
    }
}


// public class Clonador<X> {
//     @SuppressWarnings("unchecked")
//     public X clone(X x) {
//         if (x == null) {
//             return null; // Se o objeto for nulo, retorne nulo
//         }

//         // Obtemos a classe da instância do objeto x
//         Class<?> classe = x.getClass();

//         // Verificar se a classe implementa Cloneable
//         if (!Cloneable.class.isAssignableFrom(classe)) {
//             System.err.println("Aviso: o objeto da classe " + classe.getName() + " não implementa Cloneable. Retornando o objeto original.");
//             return x; // Se não for Cloneable, retorna o próprio objeto
//         }

//         // null porque chamaremos um método sem parâmetros
//         Method metodo = null;

//         // Tentamos obter o método "clone", mesmo que seja privado
//         try {
//             metodo = classe.getDeclaredMethod("clone");
//             metodo.setAccessible(true); // Garante que o método privado pode ser acessado
//         } catch (NoSuchMethodException erro) {
//             System.err.println("Erro: o método clone() não foi encontrado na classe " + classe.getName());
//             return x; // Se não houver método clone, retorna o próprio objeto
//         }

//         X ret = null;
//         try {
//             ret = (X) metodo.invoke(x); // Invoca o método clone
//         } catch (InvocationTargetException erro) {
//             System.err.println("Erro ao invocar o método clone(): " + erro.getTargetException());
//         } catch (IllegalAccessException erro) {
//             System.err.println("Acesso ilegal ao método clone(): " + erro.getMessage());
//         } catch (Exception erro) {
//             System.err.println("Erro inesperado durante a clonagem: " + erro.getMessage());
//         }

//         // Retorna o objeto clonado ou o objeto original se a clonagem falhar
//         return ret != null ? ret : x;
//     }
// }




//a