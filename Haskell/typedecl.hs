
type Pos = (Int, Int)

type Assoc k v = [(k,v)] -- List of key-value pairs.

find :: Eq k => k -> Assoc k v -> v
find k t = head [v | (k' ,v) <- t, k == k']

data Move = North | South | East | West

-- Functions on Move
move :: Move -> Pos -> Pos
move North(x,y) = (x, y+1)
move South(x,y) = (x, y-1)
move East(x,y) = (x+1, y)
move West(x,y) = (x-1, y)

moves :: [Move] -> Pos -> Pos
moves [] p = p
moves (x:xs) p = moves xs (move x p)

-- Types with arguments
data Shape = Circle Float | Rect Float Float

square :: Float -> Shape
square x = Rect x x

area :: Shape -> Float
area (Circle r) = pi * r^2
area (Rect l b) = l * b

-- Parameterised Data declarations
data MyMaybe a = MyNothing | MyJust a

-- Natural Numbers type
data Nat = Zero | Succ Nat

one = (Succ Zero)

nat2int :: Nat -> Int
nat2int Zero = 0
nat2int (Succ p) = 1 + nat2int p

int2nat :: Int -> Nat
int2nat 0 = Zero
int2nat x = Succ (int2nat (x-1))

-- Adding 2 natural numbers
add :: Nat -> Nat -> Nat
add Zero Zero = Zero
add (Succ x) Zero = Succ(x)
add Zero (Succ y) = Succ(y)
add (Succ x) (Succ y) = add (Succ (Succ x)) y

two = Succ (Succ (Zero))
three = Succ (two)
four = Succ (three)

-- Multiplication of natural numbers
mult x Zero = Zero
mult x (Succ y) = add x (mult x y)

-- Declaring lists in a similar manner
data List a = Nil | Cons a (List a) -- List can either be Nil or a Cons with a single element of type 'a' and a list.

len :: List a -> Int
len Nil       = 0
len (Cons _ xs) = 1 + len xs

-- Declaring a Tree
data Tree a = Node (Tree a) a (Tree a)| Leaf a
t :: Tree Int
t = Node (Node (Leaf 1) 3 (Leaf 4)) 5 (Node (Leaf 6) 7 (Leaf 9))

inorder :: Tree a -> [a]
inorder (Leaf x) = [x]
inorder (Node l x r) = (inorder l) ++ [x] ++ (inorder r)

-- Tree with data only in leaves
data Treex a = Leafx a | Nodex (Treex a) (Treex a)

-- Find number of leaves in a tree
nol :: Treex a -> Int
nol (Leafx a) = 1
nol (Nodex a b) = (nol a) + (nol b)

-- check if tree is balanced or not
balanced :: Treex a -> Bool
balanced (Leafx a) = True
balanced (Nodex l r) = balanced l && balanced r && abs(nol l - nol r) <= 1

-- split a list into 2 halves where each halve differs each other by 1
splitListInHalve :: [a] -> ([a], [a])
splitListInHalve x = (take l x, drop l x)
              where l = length (x) `div` 2

balance :: [a] -> Treex a
balance (x:[]) = (Leafx x)
balance (x:xs) = Nodex (balance fst) (balance snd)
            where (fst, snd) = splitListInHalve (x:xs)

ptreex :: Treex a -> [a]
ptreex (Leafx x) = [x]
ptreex (Nodex l r) = (ptreex l) ++ (ptreex r)

data Expr = Val Int | Add Expr Expr

folde :: (Int -> a) -> (a -> a -> a) -> Expr -> a
folde f g (Val x) = (f x)
folde f g (Add x y) = g (folde f g x) (folde f g y)
-- Test Case: folde (id) (+) (Add (Val 2) (Add (Val 3) (Val 4)))

-- Using folde define a function eval :: Expr -> Int that evaluates an expression to an integer value.
eval1 :: Expr -> Int
eval1 e = folde (id) (+) e

-- Using folde define a function size :: Expr -> Int that calculates number of values in the expression.
size :: Expr -> Int
size e = folde (\x->1) (+) e

-- Exercise in book
--instance Eq a => Eq (Maybe a) where
--      Just x == Just y = x == y
--      Nothing == Nothing = True
--      _ == _ = False

-- Exercise in book
-- instance Eq a => Eq [a] where
--      [a] == [b] = [a] == [b]

data MC = EVAL Expr | ADD Int | MUL Int
type Cont = [MC]

eval :: Expr -> Cont -> Int
eval (Val x) c = exec c x
eval (Add x y) c = eval x (EVAL y:c)

exec :: Cont -> Int -> Int
exec [] n = n
exec (EVAL y:c) n = eval y (ADD n:c)
exec (ADD x:c) n = exec c (x+n)
exec (MUL x:c) n = exec c (x*n)

compute :: Expr -> Int
compute e = eval e []