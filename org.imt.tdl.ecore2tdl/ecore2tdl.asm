<?xml version = '1.0' encoding = 'ISO-8859-1' ?>
<asm version="1.0" name="0">
	<cp>
		<constant value="ecore2tdl"/>
		<constant value="links"/>
		<constant value="NTransientLinkSet;"/>
		<constant value="col"/>
		<constant value="J"/>
		<constant value="main"/>
		<constant value="A"/>
		<constant value="OclParametrizedType"/>
		<constant value="#native"/>
		<constant value="Collection"/>
		<constant value="J.setName(S):V"/>
		<constant value="OclSimpleType"/>
		<constant value="OclAny"/>
		<constant value="J.setElementType(J):V"/>
		<constant value="TransientLinkSet"/>
		<constant value="A.__matcher__():V"/>
		<constant value="A.__exec__():V"/>
		<constant value="self"/>
		<constant value="__resolve__"/>
		<constant value="1"/>
		<constant value="J.oclIsKindOf(J):B"/>
		<constant value="18"/>
		<constant value="NTransientLinkSet;.getLinkBySourceElement(S):QNTransientLink;"/>
		<constant value="J.oclIsUndefined():B"/>
		<constant value="15"/>
		<constant value="NTransientLink;.getTargetFromSource(J):J"/>
		<constant value="17"/>
		<constant value="30"/>
		<constant value="Sequence"/>
		<constant value="2"/>
		<constant value="A.__resolve__(J):J"/>
		<constant value="QJ.including(J):QJ"/>
		<constant value="QJ.flatten():QJ"/>
		<constant value="e"/>
		<constant value="value"/>
		<constant value="resolveTemp"/>
		<constant value="S"/>
		<constant value="NTransientLink;.getNamedTargetFromSource(JS):J"/>
		<constant value="name"/>
		<constant value="__matcher__"/>
		<constant value="A.__matchclass2structuredType():V"/>
		<constant value="A.__matchdataType2simpleType():V"/>
		<constant value="A.__matchattribute2member():V"/>
		<constant value="A.__matchreference2member():V"/>
		<constant value="A.__matchenumLiteral2simpleDataInstance():V"/>
		<constant value="__exec__"/>
		<constant value="class2structuredType"/>
		<constant value="NTransientLinkSet;.getLinksByRule(S):QNTransientLink;"/>
		<constant value="A.__applyclass2structuredType(NTransientLink;):V"/>
		<constant value="dataType2simpleType"/>
		<constant value="A.__applydataType2simpleType(NTransientLink;):V"/>
		<constant value="attribute2member"/>
		<constant value="A.__applyattribute2member(NTransientLink;):V"/>
		<constant value="reference2member"/>
		<constant value="A.__applyreference2member(NTransientLink;):V"/>
		<constant value="enumLiteral2simpleDataInstance"/>
		<constant value="A.__applyenumLiteral2simpleDataInstance(NTransientLink;):V"/>
		<constant value="__matchclass2structuredType"/>
		<constant value="EClass"/>
		<constant value="Ecore"/>
		<constant value="IN"/>
		<constant value="MMOF!Classifier;.allInstancesFrom(S):QJ"/>
		<constant value="TransientLink"/>
		<constant value="NTransientLink;.setRule(MATL!Rule;):V"/>
		<constant value="class"/>
		<constant value="NTransientLink;.addSourceElement(SJ):V"/>
		<constant value="type"/>
		<constant value="StructuredDataType"/>
		<constant value="TDL"/>
		<constant value="NTransientLink;.addTargetElement(SJ):V"/>
		<constant value="NTransientLinkSet;.addLink2(NTransientLink;B):V"/>
		<constant value="9:5-11:42"/>
		<constant value="__applyclass2structuredType"/>
		<constant value="NTransientLink;"/>
		<constant value="NTransientLink;.getSourceElement(S):J"/>
		<constant value="NTransientLink;.getTargetElement(S):J"/>
		<constant value="3"/>
		<constant value="eAllStructuralFeatures"/>
		<constant value="member"/>
		<constant value="10:11-10:16"/>
		<constant value="10:11-10:21"/>
		<constant value="10:3-10:21"/>
		<constant value="11:13-11:18"/>
		<constant value="11:13-11:41"/>
		<constant value="11:3-11:41"/>
		<constant value="link"/>
		<constant value="__matchdataType2simpleType"/>
		<constant value="EDataType"/>
		<constant value="dataType"/>
		<constant value="SimpleDataType"/>
		<constant value="15:5-16:25"/>
		<constant value="__applydataType2simpleType"/>
		<constant value="16:11-16:19"/>
		<constant value="16:11-16:24"/>
		<constant value="16:3-16:24"/>
		<constant value="__matchattribute2member"/>
		<constant value="EAttribute"/>
		<constant value="attr"/>
		<constant value="Member"/>
		<constant value="20:5-22:26"/>
		<constant value="__applyattribute2member"/>
		<constant value="eType"/>
		<constant value="21:11-21:15"/>
		<constant value="21:11-21:20"/>
		<constant value="21:3-21:20"/>
		<constant value="22:15-22:19"/>
		<constant value="22:15-22:25"/>
		<constant value="22:3-22:25"/>
		<constant value="__matchreference2member"/>
		<constant value="EReference"/>
		<constant value="ref"/>
		<constant value="26:5-28:25"/>
		<constant value="__applyreference2member"/>
		<constant value="27:11-27:14"/>
		<constant value="27:11-27:19"/>
		<constant value="27:3-27:19"/>
		<constant value="28:15-28:18"/>
		<constant value="28:15-28:24"/>
		<constant value="28:3-28:24"/>
		<constant value="__matchenumLiteral2simpleDataInstance"/>
		<constant value="EEnumLiteral"/>
		<constant value="enumLiteral"/>
		<constant value="dataInstance"/>
		<constant value="SimpleDataInstance"/>
		<constant value="32:5-34:33"/>
		<constant value="__applyenumLiteral2simpleDataInstance"/>
		<constant value="eEnum"/>
		<constant value="33:11-33:22"/>
		<constant value="33:11-33:27"/>
		<constant value="33:3-33:27"/>
		<constant value="34:15-34:26"/>
		<constant value="34:15-34:32"/>
		<constant value="34:3-34:32"/>
	</cp>
	<field name="1" type="2"/>
	<field name="3" type="4"/>
	<operation name="5">
		<context type="6"/>
		<parameters>
		</parameters>
		<code>
			<getasm/>
			<push arg="7"/>
			<push arg="8"/>
			<new/>
			<dup/>
			<push arg="9"/>
			<pcall arg="10"/>
			<dup/>
			<push arg="11"/>
			<push arg="8"/>
			<new/>
			<dup/>
			<push arg="12"/>
			<pcall arg="10"/>
			<pcall arg="13"/>
			<set arg="3"/>
			<getasm/>
			<push arg="14"/>
			<push arg="8"/>
			<new/>
			<set arg="1"/>
			<getasm/>
			<pcall arg="15"/>
			<getasm/>
			<pcall arg="16"/>
		</code>
		<linenumbertable>
		</linenumbertable>
		<localvariabletable>
			<lve slot="0" name="17" begin="0" end="24"/>
		</localvariabletable>
	</operation>
	<operation name="18">
		<context type="6"/>
		<parameters>
			<parameter name="19" type="4"/>
		</parameters>
		<code>
			<load arg="19"/>
			<getasm/>
			<get arg="3"/>
			<call arg="20"/>
			<if arg="21"/>
			<getasm/>
			<get arg="1"/>
			<load arg="19"/>
			<call arg="22"/>
			<dup/>
			<call arg="23"/>
			<if arg="24"/>
			<load arg="19"/>
			<call arg="25"/>
			<goto arg="26"/>
			<pop/>
			<load arg="19"/>
			<goto arg="27"/>
			<push arg="28"/>
			<push arg="8"/>
			<new/>
			<load arg="19"/>
			<iterate/>
			<store arg="29"/>
			<getasm/>
			<load arg="29"/>
			<call arg="30"/>
			<call arg="31"/>
			<enditerate/>
			<call arg="32"/>
		</code>
		<linenumbertable>
		</linenumbertable>
		<localvariabletable>
			<lve slot="2" name="33" begin="23" end="27"/>
			<lve slot="0" name="17" begin="0" end="29"/>
			<lve slot="1" name="34" begin="0" end="29"/>
		</localvariabletable>
	</operation>
	<operation name="35">
		<context type="6"/>
		<parameters>
			<parameter name="19" type="4"/>
			<parameter name="29" type="36"/>
		</parameters>
		<code>
			<getasm/>
			<get arg="1"/>
			<load arg="19"/>
			<call arg="22"/>
			<load arg="19"/>
			<load arg="29"/>
			<call arg="37"/>
		</code>
		<linenumbertable>
		</linenumbertable>
		<localvariabletable>
			<lve slot="0" name="17" begin="0" end="6"/>
			<lve slot="1" name="34" begin="0" end="6"/>
			<lve slot="2" name="38" begin="0" end="6"/>
		</localvariabletable>
	</operation>
	<operation name="39">
		<context type="6"/>
		<parameters>
		</parameters>
		<code>
			<getasm/>
			<pcall arg="40"/>
			<getasm/>
			<pcall arg="41"/>
			<getasm/>
			<pcall arg="42"/>
			<getasm/>
			<pcall arg="43"/>
			<getasm/>
			<pcall arg="44"/>
		</code>
		<linenumbertable>
		</linenumbertable>
		<localvariabletable>
			<lve slot="0" name="17" begin="0" end="9"/>
		</localvariabletable>
	</operation>
	<operation name="45">
		<context type="6"/>
		<parameters>
		</parameters>
		<code>
			<getasm/>
			<get arg="1"/>
			<push arg="46"/>
			<call arg="47"/>
			<iterate/>
			<store arg="19"/>
			<getasm/>
			<load arg="19"/>
			<pcall arg="48"/>
			<enditerate/>
			<getasm/>
			<get arg="1"/>
			<push arg="49"/>
			<call arg="47"/>
			<iterate/>
			<store arg="19"/>
			<getasm/>
			<load arg="19"/>
			<pcall arg="50"/>
			<enditerate/>
			<getasm/>
			<get arg="1"/>
			<push arg="51"/>
			<call arg="47"/>
			<iterate/>
			<store arg="19"/>
			<getasm/>
			<load arg="19"/>
			<pcall arg="52"/>
			<enditerate/>
			<getasm/>
			<get arg="1"/>
			<push arg="53"/>
			<call arg="47"/>
			<iterate/>
			<store arg="19"/>
			<getasm/>
			<load arg="19"/>
			<pcall arg="54"/>
			<enditerate/>
			<getasm/>
			<get arg="1"/>
			<push arg="55"/>
			<call arg="47"/>
			<iterate/>
			<store arg="19"/>
			<getasm/>
			<load arg="19"/>
			<pcall arg="56"/>
			<enditerate/>
		</code>
		<linenumbertable>
		</linenumbertable>
		<localvariabletable>
			<lve slot="1" name="33" begin="5" end="8"/>
			<lve slot="1" name="33" begin="15" end="18"/>
			<lve slot="1" name="33" begin="25" end="28"/>
			<lve slot="1" name="33" begin="35" end="38"/>
			<lve slot="1" name="33" begin="45" end="48"/>
			<lve slot="0" name="17" begin="0" end="49"/>
		</localvariabletable>
	</operation>
	<operation name="57">
		<context type="6"/>
		<parameters>
		</parameters>
		<code>
			<push arg="58"/>
			<push arg="59"/>
			<findme/>
			<push arg="60"/>
			<call arg="61"/>
			<iterate/>
			<store arg="19"/>
			<getasm/>
			<get arg="1"/>
			<push arg="62"/>
			<push arg="8"/>
			<new/>
			<dup/>
			<push arg="46"/>
			<pcall arg="63"/>
			<dup/>
			<push arg="64"/>
			<load arg="19"/>
			<pcall arg="65"/>
			<dup/>
			<push arg="66"/>
			<push arg="67"/>
			<push arg="68"/>
			<new/>
			<pcall arg="69"/>
			<pusht/>
			<pcall arg="70"/>
			<enditerate/>
		</code>
		<linenumbertable>
			<lne id="71" begin="19" end="24"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="1" name="64" begin="6" end="26"/>
			<lve slot="0" name="17" begin="0" end="27"/>
		</localvariabletable>
	</operation>
	<operation name="72">
		<context type="6"/>
		<parameters>
			<parameter name="19" type="73"/>
		</parameters>
		<code>
			<load arg="19"/>
			<push arg="64"/>
			<call arg="74"/>
			<store arg="29"/>
			<load arg="19"/>
			<push arg="66"/>
			<call arg="75"/>
			<store arg="76"/>
			<load arg="76"/>
			<dup/>
			<getasm/>
			<load arg="29"/>
			<get arg="38"/>
			<call arg="30"/>
			<set arg="38"/>
			<dup/>
			<getasm/>
			<load arg="29"/>
			<get arg="77"/>
			<call arg="30"/>
			<set arg="78"/>
			<pop/>
		</code>
		<linenumbertable>
			<lne id="79" begin="11" end="11"/>
			<lne id="80" begin="11" end="12"/>
			<lne id="81" begin="9" end="14"/>
			<lne id="82" begin="17" end="17"/>
			<lne id="83" begin="17" end="18"/>
			<lne id="84" begin="15" end="20"/>
			<lne id="71" begin="8" end="21"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="3" name="66" begin="7" end="21"/>
			<lve slot="2" name="64" begin="3" end="21"/>
			<lve slot="0" name="17" begin="0" end="21"/>
			<lve slot="1" name="85" begin="0" end="21"/>
		</localvariabletable>
	</operation>
	<operation name="86">
		<context type="6"/>
		<parameters>
		</parameters>
		<code>
			<push arg="87"/>
			<push arg="59"/>
			<findme/>
			<push arg="60"/>
			<call arg="61"/>
			<iterate/>
			<store arg="19"/>
			<getasm/>
			<get arg="1"/>
			<push arg="62"/>
			<push arg="8"/>
			<new/>
			<dup/>
			<push arg="49"/>
			<pcall arg="63"/>
			<dup/>
			<push arg="88"/>
			<load arg="19"/>
			<pcall arg="65"/>
			<dup/>
			<push arg="66"/>
			<push arg="89"/>
			<push arg="68"/>
			<new/>
			<pcall arg="69"/>
			<pusht/>
			<pcall arg="70"/>
			<enditerate/>
		</code>
		<linenumbertable>
			<lne id="90" begin="19" end="24"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="1" name="88" begin="6" end="26"/>
			<lve slot="0" name="17" begin="0" end="27"/>
		</localvariabletable>
	</operation>
	<operation name="91">
		<context type="6"/>
		<parameters>
			<parameter name="19" type="73"/>
		</parameters>
		<code>
			<load arg="19"/>
			<push arg="88"/>
			<call arg="74"/>
			<store arg="29"/>
			<load arg="19"/>
			<push arg="66"/>
			<call arg="75"/>
			<store arg="76"/>
			<load arg="76"/>
			<dup/>
			<getasm/>
			<load arg="29"/>
			<get arg="38"/>
			<call arg="30"/>
			<set arg="38"/>
			<pop/>
		</code>
		<linenumbertable>
			<lne id="92" begin="11" end="11"/>
			<lne id="93" begin="11" end="12"/>
			<lne id="94" begin="9" end="14"/>
			<lne id="90" begin="8" end="15"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="3" name="66" begin="7" end="15"/>
			<lve slot="2" name="88" begin="3" end="15"/>
			<lve slot="0" name="17" begin="0" end="15"/>
			<lve slot="1" name="85" begin="0" end="15"/>
		</localvariabletable>
	</operation>
	<operation name="95">
		<context type="6"/>
		<parameters>
		</parameters>
		<code>
			<push arg="96"/>
			<push arg="59"/>
			<findme/>
			<push arg="60"/>
			<call arg="61"/>
			<iterate/>
			<store arg="19"/>
			<getasm/>
			<get arg="1"/>
			<push arg="62"/>
			<push arg="8"/>
			<new/>
			<dup/>
			<push arg="51"/>
			<pcall arg="63"/>
			<dup/>
			<push arg="97"/>
			<load arg="19"/>
			<pcall arg="65"/>
			<dup/>
			<push arg="78"/>
			<push arg="98"/>
			<push arg="68"/>
			<new/>
			<pcall arg="69"/>
			<pusht/>
			<pcall arg="70"/>
			<enditerate/>
		</code>
		<linenumbertable>
			<lne id="99" begin="19" end="24"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="1" name="97" begin="6" end="26"/>
			<lve slot="0" name="17" begin="0" end="27"/>
		</localvariabletable>
	</operation>
	<operation name="100">
		<context type="6"/>
		<parameters>
			<parameter name="19" type="73"/>
		</parameters>
		<code>
			<load arg="19"/>
			<push arg="97"/>
			<call arg="74"/>
			<store arg="29"/>
			<load arg="19"/>
			<push arg="78"/>
			<call arg="75"/>
			<store arg="76"/>
			<load arg="76"/>
			<dup/>
			<getasm/>
			<load arg="29"/>
			<get arg="38"/>
			<call arg="30"/>
			<set arg="38"/>
			<dup/>
			<getasm/>
			<load arg="29"/>
			<get arg="101"/>
			<call arg="30"/>
			<set arg="88"/>
			<pop/>
		</code>
		<linenumbertable>
			<lne id="102" begin="11" end="11"/>
			<lne id="103" begin="11" end="12"/>
			<lne id="104" begin="9" end="14"/>
			<lne id="105" begin="17" end="17"/>
			<lne id="106" begin="17" end="18"/>
			<lne id="107" begin="15" end="20"/>
			<lne id="99" begin="8" end="21"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="3" name="78" begin="7" end="21"/>
			<lve slot="2" name="97" begin="3" end="21"/>
			<lve slot="0" name="17" begin="0" end="21"/>
			<lve slot="1" name="85" begin="0" end="21"/>
		</localvariabletable>
	</operation>
	<operation name="108">
		<context type="6"/>
		<parameters>
		</parameters>
		<code>
			<push arg="109"/>
			<push arg="59"/>
			<findme/>
			<push arg="60"/>
			<call arg="61"/>
			<iterate/>
			<store arg="19"/>
			<getasm/>
			<get arg="1"/>
			<push arg="62"/>
			<push arg="8"/>
			<new/>
			<dup/>
			<push arg="53"/>
			<pcall arg="63"/>
			<dup/>
			<push arg="110"/>
			<load arg="19"/>
			<pcall arg="65"/>
			<dup/>
			<push arg="78"/>
			<push arg="98"/>
			<push arg="68"/>
			<new/>
			<pcall arg="69"/>
			<pusht/>
			<pcall arg="70"/>
			<enditerate/>
		</code>
		<linenumbertable>
			<lne id="111" begin="19" end="24"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="1" name="110" begin="6" end="26"/>
			<lve slot="0" name="17" begin="0" end="27"/>
		</localvariabletable>
	</operation>
	<operation name="112">
		<context type="6"/>
		<parameters>
			<parameter name="19" type="73"/>
		</parameters>
		<code>
			<load arg="19"/>
			<push arg="110"/>
			<call arg="74"/>
			<store arg="29"/>
			<load arg="19"/>
			<push arg="78"/>
			<call arg="75"/>
			<store arg="76"/>
			<load arg="76"/>
			<dup/>
			<getasm/>
			<load arg="29"/>
			<get arg="38"/>
			<call arg="30"/>
			<set arg="38"/>
			<dup/>
			<getasm/>
			<load arg="29"/>
			<get arg="101"/>
			<call arg="30"/>
			<set arg="88"/>
			<pop/>
		</code>
		<linenumbertable>
			<lne id="113" begin="11" end="11"/>
			<lne id="114" begin="11" end="12"/>
			<lne id="115" begin="9" end="14"/>
			<lne id="116" begin="17" end="17"/>
			<lne id="117" begin="17" end="18"/>
			<lne id="118" begin="15" end="20"/>
			<lne id="111" begin="8" end="21"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="3" name="78" begin="7" end="21"/>
			<lve slot="2" name="110" begin="3" end="21"/>
			<lve slot="0" name="17" begin="0" end="21"/>
			<lve slot="1" name="85" begin="0" end="21"/>
		</localvariabletable>
	</operation>
	<operation name="119">
		<context type="6"/>
		<parameters>
		</parameters>
		<code>
			<push arg="120"/>
			<push arg="59"/>
			<findme/>
			<push arg="60"/>
			<call arg="61"/>
			<iterate/>
			<store arg="19"/>
			<getasm/>
			<get arg="1"/>
			<push arg="62"/>
			<push arg="8"/>
			<new/>
			<dup/>
			<push arg="55"/>
			<pcall arg="63"/>
			<dup/>
			<push arg="121"/>
			<load arg="19"/>
			<pcall arg="65"/>
			<dup/>
			<push arg="122"/>
			<push arg="123"/>
			<push arg="68"/>
			<new/>
			<pcall arg="69"/>
			<pusht/>
			<pcall arg="70"/>
			<enditerate/>
		</code>
		<linenumbertable>
			<lne id="124" begin="19" end="24"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="1" name="121" begin="6" end="26"/>
			<lve slot="0" name="17" begin="0" end="27"/>
		</localvariabletable>
	</operation>
	<operation name="125">
		<context type="6"/>
		<parameters>
			<parameter name="19" type="73"/>
		</parameters>
		<code>
			<load arg="19"/>
			<push arg="121"/>
			<call arg="74"/>
			<store arg="29"/>
			<load arg="19"/>
			<push arg="122"/>
			<call arg="75"/>
			<store arg="76"/>
			<load arg="76"/>
			<dup/>
			<getasm/>
			<load arg="29"/>
			<get arg="38"/>
			<call arg="30"/>
			<set arg="38"/>
			<dup/>
			<getasm/>
			<load arg="29"/>
			<get arg="126"/>
			<call arg="30"/>
			<set arg="88"/>
			<pop/>
		</code>
		<linenumbertable>
			<lne id="127" begin="11" end="11"/>
			<lne id="128" begin="11" end="12"/>
			<lne id="129" begin="9" end="14"/>
			<lne id="130" begin="17" end="17"/>
			<lne id="131" begin="17" end="18"/>
			<lne id="132" begin="15" end="20"/>
			<lne id="124" begin="8" end="21"/>
		</linenumbertable>
		<localvariabletable>
			<lve slot="3" name="122" begin="7" end="21"/>
			<lve slot="2" name="121" begin="3" end="21"/>
			<lve slot="0" name="17" begin="0" end="21"/>
			<lve slot="1" name="85" begin="0" end="21"/>
		</localvariabletable>
	</operation>
</asm>
