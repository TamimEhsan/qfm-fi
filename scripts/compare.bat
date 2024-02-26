@echo off
set input_home="g:\Research\Dataset_Simulated\37-taxon"
set input_dir="g:\Research\Dataset_Simulated\37-taxon\scale2d.200g.500b"
set input_file="\R3\weighted_quartets"
set quartet_type=2

:: Run the original QFM
@REM cd "g:\Research\QFM_FI_LATEST\qfm_java"
@REM java '@C:\Users\User\AppData\Local\Temp\cp_5ixwoxjohys6vuq64brghtsr8.argfile' '-m' 'qfm_ad/qfm_fi.Main' %input_dir%%input_file% %quartet_type%
'D:\Program Files\Amazon Corretto\jdk21.0.1_12\bin\java.exe' '@C:\Users\User\AppData\Local\Temp\cp_5ixwoxjohys6vuq64brghtsr8.argfile' '-m' 'qfm_ad/qfm_fi.Main'
:: Run the modified QFM
@REM java -m qfm_ad/qfm_fi_mod.Main %input_dir%\R3\weighted_quartets %quartet_type%

:: Calculate the FP and FN and RF and store them in a var
@REM python "..\..\RF_Score\rfScoreCalculator\getFpFn.py" -t %input_home%\True.tree -e IO\output_org.txt

:: Pause to see output, remove if unnecessary
@REM pause
