declare namespace pkg="http://schemas.microsoft.com/office/2006/xmlPackage";
declare namespace saxon="http://saxon.sf.net/";
declare namespace w="http://schemas.openxmlformats.org/wordprocessingml/2006/main";
declare variable $table external;
declare option saxon:output "cdata-section-elements=content";


declare function local:root($body){
for $x in $body//w:tbl
let
$row := local:row($x)

return
<table>
{$row}
</table>
};



declare function local:row($x) {
for $tr at $ypos in $x/w:tr return
element row
{
attribute y {$ypos},

(:tc is before sdt:)
for $tc at $xpos in ($tr/w:tc, $tr/w:sdt) return
element cell {
attribute x {$xpos},
attribute y {$ypos},


(:TC p is after tc and has sdt:)
if(exists($tc/w:p//w:sdt)) then (
attribute style {$tc/w:p[1]/w:pPr/w:pStyle/@w:val},
for $p in $tc/w:p/w:sdt return
element {data($p/w:sdtPr/w:tag/@w:val)} {
data($p/w:sdtContent//w:t)
}
)


(:TH p is after tc and has t - tableheaders text:)
else if (exists($tc/w:p//w:t)) then (
attribute column-name {$tc/w:p//w:t},
for $p in $tc/w:p return
for $r in $p/w:r return
data(normalize-space($r/w:t))
)


(:TC sdt is after tc:)
else if (exists($tc/w:sdt)) then (
for $sdt in $tc/w:sdt return
element {data($sdt/w:sdtPr/w:tag/@w:val)} {
for $p in $sdt/w:sdtContent/w:p return
local:paragraph($p)
}
)


(:TC sdt is before tc - such as Unitcontent:)
else if(exists($tr/w:sdt)) then
local:section($tr/w:sdt)

else()
}
}

};


declare function local:cell($tr, $xpos, $ypos){
element cell {
attribute x {$xpos},
attribute y {$ypos},
attribute type {$tr/w:sdt/w:sdtPr/w:tag/@w:val},
(:TC sdt is before tc - such as Unitcontent:)
if(exists($tr/w:sdt)) then (
local:section($tr)
) else()
}
};

declare function local:section($sdt){

let
$tc := $sdt/w:sdtContent/w:tc
return
(
attribute style {$tc/w:p[1]/w:pPr/w:pStyle/@w:val},
attribute data-type {data($sdt/w:sdtPr/w:tag/@w:val)},
element {data($sdt/w:sdtPr/w:tag/@w:val)} {

for $p at $ppos in $tc/w:p

return

if (exists($p/w:pPr/w:numPr)) then

element li {
attribute ilvl {data($p/w:pPr/w:numPr/w:ilvl/@w:val)},
data($p/w:r/w:t)
}

else (
local:paragraph($p)
)
}
)
};


declare function local:paragraph($p){
element p {
for $r in $p/w:r return
data(normalize-space($r/w:t))
}
};


declare function local:bold($mixed-content){
for $child in $mixed-content/node()
return if ($child instance of element() and local-name($child)="bold")
then element b{data($child)}
else $child
};

local:root($table)