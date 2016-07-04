
var statsTemplate = '<p><table style="text-align:center;margin-left: auto;margin-right: auto;border-collapse: separate;">\
	<th style="border: none !important;">Specifications</th>\
	<th style="border: none !important;">Scenarios</th>\
	<tr>\
		<td style="border: none !important;">SPEC_NUMBER</td>\
		<td style="border: none !important;">SCENARIO_NUMBER</td>\
	</tr>\
</table></p>\
';
var populateIndex = function(specs) {
	if (Object.keys(specs).length == 0) {
		document.getElementsByClassName("specs")[0].innerHTML = "<p>No Specifications found that matches the given tag expression...<p>";
		return;
	}
	var text = "<ul>";
	var scenarioNumber = 0;
	specs.forEach(function(spec) {
		text += "<li><p><b><a href=\"" + spec["path"] + "\">" + spec["name"]  + "</a></b><ol>";
		spec.scenarios.forEach(function(scn) {
			text += "<li>" + scn["name"] + "</li>";
			scenarioNumber++;
		});
		text += "</ol></p></li>";
	});
	text += "</ul></div>";
	var stats = statsTemplate.replace("SPEC_NUMBER", specs.length).replace("SCENARIO_NUMBER", scenarioNumber);
	document.getElementsByClassName("specs")[0].innerHTML = stats + text;
}
function handle(e){
        if(e.keyCode === 13) {
        	if (document.getElementsByClassName("tags")[0].value.trim() === "")	{
        		populateIndex(specs);
        		return false;
        	}
   			populateIndex(filterSpecs(document.getElementsByClassName("tags")[0].value));
        }
        return false;
}
var filterSpecs = function(tagExp) {
	tags = getTagsWithoutOperators(tagExp).map(function(e) {
		return e.replace("!", "")
	});
	var newSpecs = [];
	specs.forEach(function(spec) {
		var scenarios = [];
		spec.scenarios.forEach(function(scn) {
			var newTagExp = tagExp;
			newTagExp = replace(newTagExp, scn.tags.filter(function(t) {
				return t !== "";
			}), "true");
			newTagExp = replace(newTagExp, tags, "false");
			if (eval(newTagExp)) scenarios.push(scn);
		});
		if (scenarios.length > 0) {
			spec.scenarios = scenarios;
			newSpecs.push(spec);
		}
	});
	return newSpecs;
}
var replace = function(tagExp, tags, replaceString) {
	var tagsWithOperators = getTagsWithOperators(tagExp);
	tags.forEach(function(t) {
		var index= tagsWithOperators.indexOf(t);
		if(index > -1)
			tagsWithOperators[index] = replaceString;
		index= tagsWithOperators.indexOf("!" + t);
		if(index > -1)
			tagsWithOperators[index] = "!" + replaceString;
	});
	return tagsWithOperators.join("");
}
var getTags = function(tagExp, regex) {
	return tagExp.split(regex).map(function(e) {
		return e.trim();
	});
}
var getTagsWithOperators = function(tagExp) {
	return getTags(tagExp, /(&|\|)/);
}
var getTagsWithoutOperators = function(tagExp) {
	return getTags(tagExp, /(?:&|\|)/);
}
populateIndex(specs);
