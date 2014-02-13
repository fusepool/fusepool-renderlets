package org.apache.clerezza.platform.editor

import org.apache.clerezza.rdf.core._
import impl.util.W3CDateFormat
import org.apache.clerezza.rdf.ontologies.RDFS
import org.apache.clerezza.rdf.scala.utils.Preamble._
import javax.ws.rs.core.MediaType
import org.apache.clerezza.platform.typerendering.TypeRenderlet
import org.apache.clerezza.platform.typerendering.scala._
import org.apache.clerezza.rdf.ontologies.DISCOBITS
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;import scala.xml.Unparsed



/**
 * A Renderlet for rss:items
 */
@Component
@Service(Array(classOf[TypeRenderlet]))
class UduvuduResource extends SRenderlet {

  val getRdfType = RDFS.Resource 
    
  override val getMediaType = MediaType.TEXT_HTML_TYPE

  override def getModePattern = ""

  override def renderedPage(arguments: XmlResult.Arguments) = {
    new XmlResult(arguments) {
      override def content = {
        val script = """
        // prepare visualizer templates for uduvudu
        $("#visualizer").load("/scripts/uduvudu/visualizer_browser.html");

        function loadSource(source, resource) {
            var store = rdfstore.create();
            store.load('remote',source, function(success, amountTriples){
                if(success) {
                    console.debug("successfully loaded "+amountTriples+" triples");
                    $("#main").html(uduvudu.process(store, resource));
                };
            })
        };

        source = window.location.href;
        loadSource(source, source);
        """
        <html xmlns:disco="http://discobits.org/ontology#"> 
         <head>
    <link href="/scripts/uduvudu/lib/bootstrap.min.css" rel="stylesheet" />
    <script src="/scripts/uduvudu/lib/underscore-1.5.1.js" type="text/javascript"></script>
    <script src="/scripts/uduvudu/lib/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="/scripts/uduvudu/lib/handlebars-v1.3.0.js" type="text/javascript"></script>
    <script src="/scripts/uduvudu/lib/rdfstore-0.8.1-fix.js" type="text/javascript"></script>
    <script src="/scripts/uduvudu/matcher.js" type="text/javascript"></script>
    <script src="/scripts/uduvudu/uduvudu.js" type="text/javascript"></script>
</head>
<body>
    <div class="container">
        <div id="main">
        </div>
    </div>
    <div id="visualizer">
    </div>
    <script type="text/javascript">
        {Unparsed(script)}
    </script>
</body>
</html>
      }
    }
  }

}
