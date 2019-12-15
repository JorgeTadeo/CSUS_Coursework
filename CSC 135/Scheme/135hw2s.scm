; ---------- A --------------

(define (appendInt N M)
  (if (< M 10) (+ (* N 10) M)
               (+ (* (appendInt N (quotient M 10)) 10) (modulo M 10))))

; ---------- B --------------

(define (listsMax L M)
  (cond ((null? L) L)
        ((> (car L) (car M)) (cons (car L) (listsMax (cdr L) (cdr M))))
        (else (cons (car M) (listsMax (cdr L) (cdr M))))))

; ---------- C --------------

(define (cycler L n) (additionCycle L n))

(define (additionCycle L n)
  (if (null? (cdr L)) (+ n (car L))
                      (subtractCycle (cdr L) (+ n (car L)))))
    
(define (subtractCycle L n)
  (if (null? (cdr L)) (- n (car L))
                      (multiplyCycle (cdr L) (- n (car L)))))
    
(define (multiplyCycle L n)
  (if (null? (cdr L)) (* n (car L))
                      (additionCycle (cdr L) (* n (car L)))))

; ---------- D --------------

(define (numTrues f L)
  (cond ((null? L) 0)
        ((f (car L)) (+ 1 (numTrues f (cdr L))))
        (else (numTrues f (cdr L)))))

(define (functionWinner f M N)
  (cond ((> (numTrues f M) (numTrues f N)) 1)
        ((< (numTrues f M) (numTrues f N)) 2)
        (else 0)))

; ---------- E --------------

(define (countTrue f L)
  (cond ((null? L) 0)
        ((list? (car L)) (+ (countTrue f (car L)) (countTrue f (cdr L))))
        ((f (car L)) (+ 1 (countTrue f (cdr L))))
        (else (countTrue f (cdr L)))))

; ---------- F --------------

(define (isInList n L)
  (if (null? L) #f
                (if (equal? n (car L)) #t
                                       (isInList n (cdr L)))))

(define (badNumberRemover L)
  (define (Remover M)
    (if (null? M) '()
                  (if (isInList (car M) L) (Remover (cdr M))
                                           (cons (car M) (Remover (cdr M))))))
  Remover
)

