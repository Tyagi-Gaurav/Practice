-- Subs: Return all possible sub-sequences of a list
subs:: [a] -> [[a]]
subs [] = [[]]
subs (x:xs) = others ++ map (x:) others
            where others = subs(xs)

-- Interleave: Return all possible ways to interleave an element into the list
interleave :: a -> [a] -> [[a]]
interleave x [] = [[x]]
interleave y (x:xs) = [y:x:xs] ++ map(x:) (interleave y xs)

-- Find permutations of a list
perms :: [a] -> [[a]]
perms [] = [[]]
perms (x:xs) = concat (map (interleave x) (perms xs))

choices :: [a] -> [[a]]
choices = concat . map perms . subs

-- choices using list comprehensions
choices' :: [a] -> [[a]]
choices' xs = [z | y <- subs (xs), z <- perms (y)]

data Op = Add | Sub | Mul | Div

instance Show Op where
  show Add = "+"
  show Sub = "-"
  show Mul = "*"
  show Div = "/"

-- Checks if its valid to perform an operation.
valid :: Op -> Int -> Int -> Bool
valid Add _ _ = True
valid Sub x y = x > y
valid Mul _ _ = True
valid Div x y = x `mod` y == 0

-- Function apply that applies the operation
apply :: Op -> Int -> Int -> Int
apply Add x y = x + y
apply Sub x y = x - y
apply Mul x y = x * y
apply Div x y = x `div` y

data Expr = Val Int | App Op Expr Expr

instance Show Expr where
  show (Val n) = show n
  show (App o l r) = brak l ++ show o ++ brak r
                    where
                    brak (Val n) = show n
                    brak e = "(" ++ show e ++")"

-- Example: (App Add (Val 1) (App Mul (Val 2) (Val 3)))

-- Decide if one list is taken from another
isChoice :: Eq a => [a] -> [a] -> Bool
isChoice _ [] = False
isChoice [] (y:ys) = True
isChoice (x:xs) ys | elem x ys = isChoice xs (removeFirstOccurrenceFromList x ys)
                   | otherwise = False

removeFirstOccurrenceFromList :: Eq a => a -> [a] -> [a]
removeFirstOccurrenceFromList _ [] = []
removeFirstOccurrenceFromList x (y:ys) | x == y = ys
                                       | otherwise = y:(removeFirstOccurrenceFromList x ys)