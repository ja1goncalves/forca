import org.scalajs.dom
import dom.document
import dom.html
import dom.CanvasRenderingContext2D
import scala.scalajs.js.annotation.JSExportTopLevel

object Index {

	val chances: Int = 7
	var tentativas: Int = 0

	var acertos: String = ""
	var palavra_desafio: String = ""
	val letras_tentadas: List[String] = List()

	def letrasAcertadas(div_letras: dom.html.Div): Unit = {
		var new_acertos = ""
		if(letras_tentadas.isEmpty){
			for(i <- 0 to palavra_desafio.length-1){
				new_acertos += " _"
			}
		}else{
			if(palavra_desafio.contains(letras_tentadas.head)){
				for(i <- 0 to palavra_desafio.length-1){
					if(palavra_desafio.charAt(i).toString == letras_tentadas.head){
						new_acertos = new_acertos + " " + (palavra_desafio.charAt(i).toString).map(_.toUpper)
					}else
						new_acertos += " _"
				}
			}
		}
		acertos = new_acertos
		div_letras.innerHTML = ""

		val title = document.createElement("h1")
		title.appendChild(document.createTextNode(acertos))
		div_letras.appendChild(title)
		div_letras.style.textAlign = "center"
	}
  
    @JSExportTopLevel("desafiar")
    def desafiar(palavra: dom.html.Input, desafio: dom.html.Button, aposta: dom.html.Button, letras: dom.html.Div, information: dom.html.Div, p_tentativas: dom.html.Element) = {
    	palavra_desafio = palavra.value.toUpperCase
		desafio.disabled = true
		aposta.disabled = false

		val desistir = document.getElementById("desistir").asInstanceOf[dom.html.Button]
		desistir.disabled = false

		p_tentativas.appendChild(document.createTextNode("Tentativas: " + tentativas))
		information.appendChild(p_tentativas)

		letrasAcertadas(letras)
    }

    @JSExportTopLevel("apostarLetra")
    def apostarLetra(letra: dom.html.Input, letras: dom.html.Div) = {
		if(this.tentativas < this.chances){
			val _letra: String = letra.value.charAt(0).toString.map(_.toUpper)
			if(letras_tentadas.contains(_letra)) dom.window.alert("Letra já foi tentada!")
			else{
				_letra :: letras_tentadas
				println(letras_tentadas)
				this.letrasAcertadas(letras)
			}
			
			tentativas += 1
		}else if(this.tentativas == this.chances){
			if(letra.value == palavra_desafio){
				letras.innerHTML = ""
				dom.window.alert("Fim do jogo! Você venceu!")
			}else {
				letras.innerHTML = ""
				dom.window.alert("Se fudeu!")
			}
		}else
			dom.window.alert("Já era, boy!")
    }


    @JSExportTopLevel("desistirDoJogo")
    def desistirDoJogo(desistir: dom.html.Button, aposta: dom.html.Button, desafiar: dom.html.Button, letras: dom.html.Div) = {
    	desistir.disabled = true
    	desafiar.disabled = false
		aposta.disabled = true

		val p_tentativas = document.getElementById("tentativas").asInstanceOf[dom.html.Element]
		p_tentativas.innerHTML = ""

    	letras.innerHTML = ""
    }
    

    def main(args:Array[String]):Unit = {
      
    }
}