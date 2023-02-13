
import com.supermart.cli.ASCIIArtGenerator;
import com.supermart.cli.Cli;
import com.supermart.cli.ASCIIArtGenerator.ASCIIArtFont;

public class MainClass {

	public static void main(String[] args) throws Exception {
		ASCIIArtGenerator artGen = new ASCIIArtGenerator();
		
		System.out.println();
        artGen.printTextArt("SUPERMART", ASCIIArtGenerator.ART_SIZE_MEDIUM);
        System.out.println();
         
        //System.out.println();
        //artGen.printTextArt("SUPERMART", ASCIIArtGenerator.ART_SIZE_MEDIUM, ASCIIArtFont.ART_FONT_MONO,"*");
        //System.out.println();
        
		Cli.loopInput();
	}

}
