for (i <- 1 to 4) println(i)

for (i <- 1 to 4;
     j <- 1 to 5) println(j)

for (i <- 1 to 4;
     j <- 1 to 5
     if (j%2 == 0)) println(j)

for (i <- 1 to 4;
     j <- 1 to 5
     if (j%2 == 0)) yield j

