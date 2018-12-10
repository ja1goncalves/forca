import org.scalajs.dom
import dom.document
import dom.html
import dom.CanvasRenderingContext2D
import scala.scalajs.js.annotation.JSExportTopLevel

object Index {

	val chances: Int = 6
	var tentativas: Int = 0
	val backgrounds: Array[String] = Array(
		"url('imgs/forca0.png')",
		"url('imgs/forca1.png')",
		"url('imgs/forca2.png')",
		"url('imgs/forca3.png')",
		"url('imgs/forca4.png')",
		"url('imgs/forca5.png')",
		"url('imgs/forca6.png')"
	)

	var acertos: String = ""
	var palavra_desafio: String = ""
	var letras_tentadas: List[String] = List()

	def letrasAcertadas(div_letras: dom.html.Div, p_tentativas: dom.html.Element, div_forca: dom.html.Div): Unit = {
		var new_acertos = ""
		if(letras_tentadas.isEmpty){
			for(i <- 0 to palavra_desafio.length-1){
				new_acertos += " _"
			}
		}else{
			if(palavra_desafio.contains(letras_tentadas.head)){
				for(i <- 0 to palavra_desafio.length-1){
					if(letras_tentadas.contains(palavra_desafio.charAt(i).toString)){
						new_acertos = new_acertos + " " + (palavra_desafio.charAt(i).toString).map(_.toUpper)
					}else
						new_acertos += " _"
				}
			}else{
				new_acertos = acertos
				letras_tentadas = letras_tentadas.tail
			} 
		}
		if(new_acertos == acertos) {
			tentativas = tentativas + 1
			this.alterarTentativas(p_tentativas)
			div_forca.style.backgroundImage = this.backgrounds(tentativas)
		}
		acertos = new_acertos
		div_letras.innerHTML = ""

		val title = document.createElement("h1")
		title.appendChild(document.createTextNode(acertos))
		div_letras.appendChild(title)
		div_letras.style.top = "50%"
		div_letras.style.textAlign = "center"

		if(this.isFimDeJogo) dom.window.alert("Digite a palavra desafiada pra vencer o jogo!")
		else if(tentativas == chances) dom.window.alert("Digite a palavra para vencer!")
	}

	def isFimDeJogo(): Boolean = {
		return ((acertos.toUpperCase.filterNot(_ == ' ') == palavra_desafio) || tentativas == chances)
	}

	def fimDeJogo(vitoria: Boolean, limiteDeTentativas: Boolean, desistiu: Boolean): Unit = {
		this.letras_tentadas = List()
		this.acertos = ""
		this.palavra_desafio = ""
		this.tentativas = 0

		val palavra = document.getElementById("palavra").asInstanceOf[dom.html.Input]
		val desistir = document.getElementById("desistir").asInstanceOf[dom.html.Button]
		val desafio = document.getElementById("desafio").asInstanceOf[dom.html.Button]
		val aposta = document.getElementById("aposta").asInstanceOf[dom.html.Button]
		val p_tentativas = document.getElementById("tentativas").asInstanceOf[dom.html.Element]
		val letras = document.getElementById("letras").asInstanceOf[dom.html.Div]
		val letra = document.getElementById("letra").asInstanceOf[dom.html.Input]
		val div_forca = document.getElementById("forca").asInstanceOf[dom.html.Div]

		letra.disabled = true
		palavra.disabled = false
    	desistir.disabled = true
    	desafio.disabled = false
		aposta.disabled = true
		p_tentativas.innerHTML = ""
    	letras.innerHTML = ""
		div_forca.style.backgroundImage = this.backgrounds(tentativas)

		if(desistiu) dom.window.alert("Frouxo!")
		else{
			if(vitoria) dom.window.alert("Fim do jogo! fazer o que, né? Você venceu!")
			else if(limiteDeTentativas) dom.window.alert("Você falhou com a nação!")
		}
		
	}

	def alterarTentativas(p_tentativas: dom.html.Element): Unit = {
		p_tentativas.innerHTML = ""
		p_tentativas.appendChild(document.createTextNode("Tentativas: " + tentativas))
	}
  
    @JSExportTopLevel("desafiar")
    def desafiar(palavra: dom.html.Input, desafio: dom.html.Button, aposta: dom.html.Button, letras: dom.html.Div, information: dom.html.Div, p_tentativas: dom.html.Element) = {
    	palavra_desafio = palavra.value.toUpperCase
		palavra.disabled = true
		desafio.disabled = true
		aposta.disabled = false
		
		val desistir = document.getElementById("desistir").asInstanceOf[dom.html.Button]
		desistir.disabled = false

		val letra = document.getElementById("letra").asInstanceOf[dom.html.Input]
		letra.disabled = false

		val div_forca = document.getElementById("forca").asInstanceOf[dom.html.Div]

		p_tentativas.appendChild(document.createTextNode("Tentativas: " + tentativas))
		information.appendChild(p_tentativas)

		letrasAcertadas(letras, p_tentativas, div_forca)
    }

    @JSExportTopLevel("apostarLetra")
    def apostarLetra(letra: dom.html.Input, letras: dom.html.Div, p_tentativas: dom.html.Element) = {
		if(this.tentativas < this.chances  || (this.tentativas == this.chances && letra.value.map(_.toUpper) == palavra_desafio)){
			if(letra.value.map(_.toUpper) == palavra_desafio) this.fimDeJogo(true, false, false)
			else{
				val _letra: String = letra.value.charAt(0).toString.map(_.toUpper)
				if(letras_tentadas.contains(_letra)) dom.window.alert("Letra já foi tentada!")
				else{
					val div_forca = document.getElementById("forca").asInstanceOf[dom.html.Div]
					letras_tentadas = _letra :: letras_tentadas
					this.letrasAcertadas(letras, p_tentativas, div_forca)
				}
			}
		}else if(this.isFimDeJogo){
			if(letra.value == palavra_desafio) this.fimDeJogo(true, false, false)
			else this.fimDeJogo(false, true, false)
		}else this.fimDeJogo(false, true, false)
    }


    @JSExportTopLevel("desistirDoJogo")
    def desistirDoJogo(desistir: dom.html.Button, aposta: dom.html.Button, desafiar: dom.html.Button, letras: dom.html.Div) = {
		this.fimDeJogo(false, false, true)
    }
    

    def main(args:Array[String]):Unit = {
      
    }
}