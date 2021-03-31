package hr.fer.zemris.java.custom.scripting.demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Class that parses a {@code SmartScript} document into a tree and
 * then by applying visitor pattern it reproduces the aproximate
 * original form of the document.
 * 
 * @author Marko
 *
 */
public class TreeWriter {
	
	
	/**
	 * Implementation of INodeVisitor that implements
	 * visitor design pattern to print out the aproximate
	 * exact representation of smart script document
	 * 
	 * @author Marko
	 *
	 */
	private static class WriterVisitor implements INodeVisitor{

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText().substring(2));
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print("{$ FOR "+ node.getVariable() + " " + node.getStartExpression() + " " +
					node.getEndExpression() + " " + node.getStepExpression() + " $}");
			System.out.print("{$ END $}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(" {$ =");
			for(Element element : node.getElements()) {
				System.out.print(" " + element.asText());
			}
			System.out.print(" $}");
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			
		}
		
	}
	
	
	/**
	 * Main method, it takes one command line argument which should be the path
	 * to the file to parse.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			throw new IllegalArgumentException("I expected one argument, path to the file to parse!");
		}
		
		String paths = new File(args[0]).getAbsolutePath();
		Path path = Paths.get(paths);
		
		try {
			String docBody = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			SmartScriptParser p = new SmartScriptParser(docBody);
			WriterVisitor visitor = new WriterVisitor();
			p.getDocumentNode().accept(visitor);
		} catch (IOException e) {
			System.out.println("Opening of the file did not succed");
		}
		
	}

}
