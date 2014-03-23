![SChemP Splash Screen](https://raw.githubusercontent.com/ScottKolo/SChemP/master/img/Screencaps/splash.png)

SChemP (pronounced "Shemp," like the Stooge) is short for Scott's Chemistry Program, a Java application I wrote in my senior year of high school in 2005/2006.  I had exhausted my high school's chemistry and computer science courses, so I combined the two in an independent study course where I coded SChemP from scratch (save for a few libraries).

I presented my program to a group of the school's science teachers and was passed.  Yay!

Since then, the code sat in my archive, but I figure I may as well release it to the world.  While I have made a few updates to keep it compiling without errors/warnings on Java 7, it is by no means a good example of program structure or programming paradigms.

SChemP has three parts:

1. An interactive periodic table. Elements can be clicked to reveal information such as atomic mass, electron configuration, atomic radius, etc.  It reads this data in from an XML file and allows sorting to reveal "high" and "low" regions of given properties (e.g. top right having high electronegativity, lower left having very low electronegativity).
![Periodic Table](https://raw.githubusercontent.com/ScottKolo/SChemP/master/img/Screencaps/PeriodicTable.png)

2. A chemical equation balancer.  Type in a chemical equation such as Fe + Cl2 = FeCl3 and have it balanced to 2 Fe + 3 Cl2 = 2 FeCl3. My personal favorite is H2+Ca(CN)2+NaAlF4+FeSO4+MgSiO3+KI
+H3PO4+PbCrO4+BrCl+CF2Cl2+SO2 = PbBr2+CrCl3+MgCO3+KAl(OH)4+Fe(SCN)3+
PI3+NaSiO3+CaF2+H2O.  Give it a try!
![Equation Balancer](https://raw.githubusercontent.com/ScottKolo/SChemP/master/img/Screencaps/Bal1.png)

3. A quantum mechanical orbital plotter.  It uses a crude probabilistic plotting approach (random points colored darker for higher probability), but you can still see the shapes (concentric spheres for an *s* orbital, dumbbell-shaped *p* orbitals, etc).
![Orbital Plotter](https://raw.githubusercontent.com/ScottKolo/SChemP/master/img/Screencaps/Orb1.png)

4. There was a fourth section planned for chemistry calculations (e.g. dimensional analysis), but I never completed it.  There is still some code referencing this.

SChemP is licensed under the new BSD license and includes four JAR libraries:

* [jmathplot](https://sites.google.com/site/mulabsltd/products/jmathplot) and [jmatharray](https://sites.google.com/site/mulabsltd/products/jmathplot), both written by µ-labs and licensed under the new BSD license.
* [JDOM](http://www.jdom.org/), licensed under an Apache-style license.
* The [Apache Commons Mathematics Library](http://commons.apache.org/proper/commons-math/), licensed under the Apache License 2.0.

For more information regarding licenses, please see the [LICENSE](https://github.com/ScottKolo/SChemP/blob/master/LICENSE) file.