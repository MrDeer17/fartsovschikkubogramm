package src.main;

import com.Zrips.CMI.CMI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.bukkit.Bukkit.getLogger;

public class singleton {


    public static String DEFCOLOR = "";
    public static List<String> returer = new ArrayList<>();
    public static List<String> infoF = new ArrayList<>();
    public static List<String> infoL = new ArrayList<>();
    public static List<String> infoD = new ArrayList<>();
    public static List<String> datesArray = new ArrayList<>();
    public static List<ssound> Sounds = new ArrayList<>();

    public static List<matlip> ItemsForSale = new ArrayList<>();
    public static List<matlip> ItemsForBuy = new ArrayList<>();
    public static List<List<matquest>> ItemsForLVL = new ArrayList<>();
    public static List<matlip> ItemsForDonateShop = new ArrayList<>();
    public static List<List<challenge>> ItemsChallenges = new ArrayList<>();
    public static int[][] ChestPosition = new int[10][10];
    public static double[][] Gainer = new double[4][10]; //[Buy/Sell/BuyLimit/SellLimit] [LVL]
    public static double[] MoneyPerLevel = new double[10];
    public static List<List<String>> CommandsPerLevel = new ArrayList<>();
    public static double[] BlocksPerLevel = new double[10];

    public static double[] BlocksMinReq = new double[10];
    public static String menu1;
    public static String menu2;
    public static String menu3;
    public static String menu4;

    public static List<String> Messgaes = new ArrayList<>();
    //0: limit=0; 1: items=0; 2: items=0 & FullSell; 3: items < 64 & StackSell;
    //4: limit=0 | Сдача; 5: limit=0 on this iteration | Сдача; 6: items=0 | Сдача;
    //7: items=0 & FullSell | Сдача; 8: items < 64 & StackSell | Сдача; 9: RewardRecieved
    //10: TradesUPD; 11: NotLVL; 12: MoneyREQ; 13: NOPISYAPOPA ; 14:MAXLevelReached

    public static Map<String, String> Translations = new HashMap<>();
    public static Map<String, String> Explain = new HashMap<>();
    public static Map<String, String> ExplainNull = new HashMap<>();

    public static int refreshPrice;
    public static String refresh;
    public static List<String> refreshLore = new ArrayList<>();
    public static String infoBook;
    public static List<String> infoBookLore = new ArrayList<>();
    public static String fartsovshik;
    public static List<String> fartsovshikLore = new ArrayList<>();
    public static String levelUpping;
    public static List<String> levelUppingLore = new ArrayList<>();
    public static String donate;
    public static List<String> donateLore = new ArrayList<>();

    public static String itembuy;
    public static List<String> itembuyLore = new ArrayList<>();
    public static String itemsell;
    public static List<String> itemsellLore = new ArrayList<>();

    public static String itemgetaway;
    public static List<String> itemgetawayLore = new ArrayList<>();
    public static String moneyrequired;
    public static List<String> moneyrequiredLore = new ArrayList<>();
    public static String challengeitem;
    public static List<String> challengeitemLore = new ArrayList<>();


    public static void LastPreparation() {
        menu1 = processString(menu1);
        menu2 = processString(menu2);
        menu3 = processString(menu3);
        menu4 = processString(menu4);

        for (int i = 0; i < Messgaes.size(); i++) {
            Messgaes.set(i, processString(Messgaes.get(i)));
        }
        for (int i = 0; i < returer.size(); i++) {
            returer.set(i, processString(returer.get(i)));
        }
        for (int i = 0; i < infoF.size(); i++) {
            infoF.set(i, processString(infoF.get(i)));
        }
        for (int i = 0; i < infoL.size(); i++) {
            infoL.set(i, processString(infoL.get(i)));
        }
        for (int i = 0; i < infoD.size(); i++) {
            infoD.set(i, processString(infoD.get(i)));
        }

        infoBook = processString(infoBook);
        fartsovshik = processString(fartsovshik);
        levelUpping = processString(levelUpping);
        donate = processString(donate);
        refresh = processString(refresh);
        /*itembuy = processString(itembuy);
        itemsell = processString(itemsell);*/
        for (int i = 0; i < infoBookLore.size(); i++) {
            infoBookLore.set(i, processString(infoBookLore.get(i)));
        }
        for (int i = 0; i < fartsovshikLore.size(); i++) {
            fartsovshikLore.set(i, processString(fartsovshikLore.get(i)));
        }
        for (int i = 0; i < levelUppingLore.size(); i++) {
            levelUppingLore.set(i, processString(levelUppingLore.get(i)));
        }
        for (int i = 0; i < donateLore.size(); i++) {
            donateLore.set(i, processString(donateLore.get(i)));
        }
        for (int i = 0; i < refreshLore.size(); i++) {
            refreshLore.set(i, processString(refreshLore.get(i)));
        }


        for (Map.Entry<String, String> entry : Translations.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Обновление значения элемента
            Translations.put(key, processString(value));
        }


        /*for (int i = 0; i < itembuyLore.size(); i++) {
            itembuyLore.set(i, processString(itembuyLore.get(i)));
        }
        for (int i = 0; i < itemsellLore.size(); i++) {
            itemsellLore.set(i, processString(itemsellLore.get(i)));
        }
        for (int i = 0; i < itemgetawayLore.size(); i++) {
            itemgetawayLore.set(i, processString(itemgetawayLore.get(i)));
        }
        for (int i = 0; i < moneyrequiredLore.size(); i++) {
            moneyrequiredLore.set(i, processString(moneyrequiredLore.get(i)));
        }
        for (int i = 0; i < challengeitemLore.size(); i++) {
            challengeitemLore.set(i, processString(challengeitemLore.get(i)));
        }*/
    }

    public static String processString(String input) {
        String[] parts = input.split("<");
        String result = "";
        if(parts.length <= 1) {
            return input;
        }
        for(int i = 1; i < parts.length; i++) {
            String tag = parts[i];
            String[] tagParts = tag.split(">");
            String startColor = tagParts[0].split("'")[0];
            String endColor = tagParts[0].split("'")[1];
            String textToGradientize = tagParts[1];
            String gradientText = genGr(textToGradientize, startColor, endColor);
            result += gradientText;
        }
        return result;
    }

    public static String genGr(String text, String startColor, String endColor) {
        if (endColor.equalsIgnoreCase("NULL")) {
            return ChatColor.getByChar(startColor.charAt(0)) + text;
        }
        else if(endColor.equalsIgnoreCase("-")) {
            return "  ";
        }

        StringBuilder result = new StringBuilder();

        // Разбиваем текст на символы
        char[] chars = text.toCharArray();

        // Разбиваем цвета на компоненты
        int[] startColors = hexToRgb(startColor);
        int[] endColors = hexToRgb(endColor);

        // Рассчитываем шаги для каждого компонента цвета
        double[] steps = new double[3];
        for (int i = 0; i < 3; i++) {
            steps[i] = (endColors[i] - startColors[i]) / (double) (chars.length - 1);
        }

        // Создаем градиент
        for (int i = 0; i < chars.length; i++) {
            // Рассчитываем компоненты цвета для текущего символа
            int[] currentColors = new int[3];
            for (int j = 0; j < 3; j++) {
                currentColors[j] = (int) Math.round(startColors[j] + steps[j] * i);
            }

            // Преобразуем компоненты цвета в hex-строку
            String hexColor = String.format("#%02x%02x%02x", currentColors[0], currentColors[1], currentColors[2]);

            // Добавляем символ с цветом в результат
            result.append(ChatColor.of(hexColor)).append(chars[i]);
        }

        return result.toString();
    }

    // Метод для преобразования hex-строки в массив цветовых компонентов
    private static int[] hexToRgb(String hexColor) {
        int[] rgb = new int[3];
        rgb[0] = parseInt(hexColor.substring(1, 3), 16);
        rgb[1] = parseInt(hexColor.substring(3, 5), 16);
        rgb[2] = parseInt(hexColor.substring(5, 7), 16);
        return rgb;
    }

    public static ItemStack createItemStack(String input) {
        if (!input.contains("[")) {
            Material material = Material.getMaterial(input);
            if (material == null) {
                return null;
            }
            return new ItemStack(material);
        } //IfWithoutEnchs

        String[] parts = input.split("\\[");
        String materialName = parts[0];
        String[] enchantments = parts[1].replaceAll("\\]", "").split(";");

        Material material = Material.getMaterial(materialName);
        if (material == null) {
            return null;
        }

        ItemStack itemStack = new ItemStack(material);
        for (String enchantment : enchantments) {
            String[] enchantmentParts = enchantment.split("'");
            if (enchantmentParts.length != 2) {
                continue;
            }
            Enchantment enchantmentType = Enchantment.getByName(enchantmentParts[0]);
            if (enchantmentType == null) {
                continue;
            }
            int level = parseInt(enchantmentParts[1]);
            itemStack.addUnsafeEnchantment(enchantmentType, level);
        }

        return itemStack;
    }

    public static void ReadFile(String FileName) {
        String ret = "";
        File folder = new File("plugins/market");
        if (!(folder.exists() && folder.isDirectory())) {
            try {
                Files.createDirectories(Paths.get(folder + ""));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (FileName == "menus.yml") {
            File file = new File("plugins/market/" + FileName);
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        // Файл успешно создан
                        // Теперь нужно записать в него нужный текст
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                            writer.write("//Если нужен пробел в описании использовать <StartColor:EndColor> с любыми значениями\n" +
                                    "//Минимальное количество символов в описании - 2\n" +
                                    "\n" +
                                    "_menu1:\n" +
                                    " .infobook:<StartColor:EndColor>ЗАГОЛОВОК\n" +
                                    "Описание предмета создаётся путём нажатия ENTER после заголовка\n" +
                                    "Строк может быть сколько угодно\n" +
                                    "Если понадобится этот файл заново просто удали его и запусти сервер заново\n" +
                                    "Если градиент не нужен, ENDCOLOR должен быть значением -1\n" +
                                    " .fartsovshik:<StartColor:EndColor>ЗАГОЛОВОК\n" +
                                    " .levelUpping:<StartColor:EndColor>ЗАГОЛОВОК\n" +
                                    " .donate:<StartColor:EndColor>ЗАГОЛОВОК\n" +
                                    "\n" +
                                    "\n" +
                                    "_fartsovshik:\n" +
                                    " .itembuy:<StartColor:EndColor>%item% %price% $\n" +
                                    "Продать один предмет (ЛКМ)\n" +
                                    "Продать стак предметов (ЛКМ+SHIFT)\n" +
                                    "Продать все предметы (ПКМ+SHIFT)\n" +
                                    "Осталось: %amount%\n" +
                                    " .itemsell:<StartColor:EndColor>%item% %price% $\n" +
                                    "Продать один предмет (ЛКМ)\n" +
                                    "Продать стак предметов (ЛКМ+SHIFT)\n" +
                                    "Продать все предметы (ПКМ+SHIFT)\n" +
                                    "Ещё можно сдать: %amount%");
                        } catch (IOException e) {
                            getLogger().log(Level.SEVERE, "Failed to write to " + FileName, e);
                        }
                    } else {
                        getLogger().warning("Failed to create " + FileName);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            int readId = 0;
            Stream<String> lines = null;
            try {
                lines = Files.lines(Paths.get(file + ""))
                        .map(line -> line.split("\n"))
                        .flatMap(Arrays::stream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (String line : lines.toArray(String[]::new)) {
                String value = "";
                String[] positionStrings = new String[2];
                // Пропускаем строки, начинающиеся с _
                if (line.length() <= 1 || line.startsWith("//")) {

                } else if (line.startsWith("_")) {
                    if (line.contains("menu1")) {
                        String[] parts = line.split(":");
                        menu1 = parts[1];
                    } else if (line.contains("fartsovshik")) {
                        String[] parts = line.split(":");
                        menu2 = parts[1];
                    } else if (line.contains("lvl")) {
                        String[] parts = line.split(":");
                        menu3 = parts[1];
                    } else if (line.contains("donate")) {
                        String[] parts = line.split(":");
                        menu4 = parts[1];
                    }
                } else if (line.length() >= 3 && line.contains(" .")) {
                    String[] parts = line.split(":", 2);
                    String key = parts[0].substring(1);
                    value = parts[1];
                    switch (key) {
                        case ".infobook":
                            infoBook = value;
                            readId = 1;
                            break;
                        case ".fartsovshik":
                            fartsovshik = value;
                            readId = 2;
                            break;
                        case ".levelUpping":
                            levelUpping = value;
                            readId = 3;
                            break;
                        case ".donate":
                            donate = value;
                            readId = 4;
                            break;
                        case ".refresh":
                            refresh = value;
                            readId = 100;
                            break;
                        case ".itembuy":
                            itembuy = value;
                            readId = 5;
                            break;
                        case ".itemsell":
                            itemsell = value;
                            readId = 6;
                            break;
                        case ".itemgetaway":
                            readId = 7;
                            itemgetaway = value;
                            break;
                        case ".moneyrequired":
                            readId = 8;
                            moneyrequired = value;
                            break;
                        case ".challengeitem":
                            readId = 9;
                            challengeitem = value;
                            break;
                        case ".chest10":
                            positionStrings = value.split(",");
                            for (int j = 0; j < positionStrings.length; j++) {
                                String positionString = positionStrings[j];
                                for (int k = 0; k < positionString.length(); k++) {
                                    ChestPosition[9][j * 5 + k] = Character.getNumericValue(positionString.charAt(k));
                                }
                            }
                            break;
                        case ".chest9":
                            positionStrings = value.split(",");
                            for (int j = 0; j < positionStrings.length; j++) {
                                String positionString = positionStrings[j];
                                for (int k = 0; k < positionString.length(); k++) {
                                    ChestPosition[8][j * 5 + k] = Character.getNumericValue(positionString.charAt(k));
                                }
                            }
                            break;
                        case ".chest8":
                            positionStrings = value.split(",");
                            for (int j = 0; j < positionStrings.length; j++) {
                                String positionString = positionStrings[j];
                                for (int k = 0; k < positionString.length(); k++) {
                                    ChestPosition[7][j * 5 + k] = Character.getNumericValue(positionString.charAt(k));
                                }
                            }
                            break;
                        case ".chest7":
                            positionStrings = value.split(",");
                            for (int j = 0; j < positionStrings.length; j++) {
                                String positionString = positionStrings[j];
                                for (int k = 0; k < positionString.length(); k++) {
                                    ChestPosition[6][j * 5 + k] = Character.getNumericValue(positionString.charAt(k));
                                }
                            }

                            break;
                        case ".chest6":
                            positionStrings = value.split(",");
                            for (int j = 0; j < positionStrings.length; j++) {
                                String positionString = positionStrings[j];
                                for (int k = 0; k < positionString.length(); k++) {
                                    ChestPosition[5][j * 5 + k] = Character.getNumericValue(positionString.charAt(k));
                                }
                            }

                            break;
                        case ".chest5":
                            positionStrings = value.split(",");
                            for (int j = 0; j < positionStrings.length; j++) {
                                String positionString = positionStrings[j];
                                for (int k = 0; k < positionString.length(); k++) {
                                    ChestPosition[4][j * 5 + k] = Character.getNumericValue(positionString.charAt(k));
                                }
                            }

                            break;
                        case ".chest4":
                            positionStrings = value.split(",");
                            for (int j = 0; j < positionStrings.length; j++) {
                                String positionString = positionStrings[j];
                                for (int k = 0; k < positionString.length(); k++) {
                                    ChestPosition[3][j * 5 + k] = Character.getNumericValue(positionString.charAt(k));
                                }
                            }

                            break;
                        case ".chest3":
                            positionStrings = value.split(",");
                            for (int j = 0; j < positionStrings.length; j++) {
                                String positionString = positionStrings[j];
                                for (int k = 0; k < positionString.length(); k++) {
                                    ChestPosition[2][j * 5 + k] = Character.getNumericValue(positionString.charAt(k));
                                }
                            }

                            break;
                        case ".chest2":
                            positionStrings = value.split(",");
                            for (int j = 0; j < positionStrings.length; j++) {
                                String positionString = positionStrings[j];
                                for (int k = 0; k < positionString.length(); k++) {
                                    ChestPosition[1][j * 5 + k] = Character.getNumericValue(positionString.charAt(k));
                                }
                            }
                            break;
                        case ".chest1":
                            positionStrings = value.split(",");
                            for (int j = 0; j < positionStrings.length; j++) {
                                String positionString = positionStrings[j];
                                for (int k = 0; k < positionString.length(); k++) {
                                    ChestPosition[0][j * 5 + k] = Character.getNumericValue(positionString.charAt(k));
                                }
                            }
                            break;
                        case ".menuopenS":
                            positionStrings = value.split(",");
                            String soundName = positionStrings[0];
                            float pitch = Float.parseFloat(positionStrings[1]);
                            Sounds.add(new ssound(pitch, Sound.valueOf(soundName)));
                            break;
                        case ".lvlupS":
                            positionStrings = value.split(",");
                            soundName = positionStrings[0];
                            pitch = Float.parseFloat(positionStrings[1]);
                            Sounds.add(new ssound(pitch, Sound.valueOf(soundName)));
                            break;
                        case ".itemgiveawayS":
                            positionStrings = value.split(",");
                            soundName = positionStrings[0];
                            pitch = Float.parseFloat(positionStrings[1]);
                            Sounds.add(new ssound(pitch, Sound.valueOf(soundName)));
                            break;
                        case ".moneygiveawayS":
                            positionStrings = value.split(",");
                            soundName = positionStrings[0];
                            pitch = Float.parseFloat(positionStrings[1]);
                            Sounds.add(new ssound(pitch, Sound.valueOf(soundName)));
                            break;
                        case ".itembuyS":
                            positionStrings = value.split(",");
                            soundName = positionStrings[0];
                            pitch = Float.parseFloat(positionStrings[1]);
                            Sounds.add(new ssound(pitch, Sound.valueOf(soundName)));
                            break;
                        case ".itemsellS":
                            positionStrings = value.split(",");
                            soundName = positionStrings[0];
                            pitch = Float.parseFloat(positionStrings[1]);
                            Sounds.add(new ssound(pitch, Sound.valueOf(soundName)));
                            break;
                        case ".getback":
                            returer.add(value);
                            readId = 10;
                            break;
                        case ".infofartsovshik":
                            infoF.add(value);
                            readId = 11;
                            break;
                        case ".infolvl":
                            infoL.add(value);
                            readId = 12;
                            break;
                        case ".infodonate":
                            infoD.add(value);
                            readId = 13;
                            break;

                        default:
                            getLogger().warning("Unknown key in config.yml: " + key);
                            readId = 0;
                    }
                } else {
                    if (readId == 1) {
                        infoBookLore.add(line.trim());
                    } else if (readId == 2) {
                        fartsovshikLore.add(line.trim());
                    } else if (readId == 3) {
                        levelUppingLore.add(line.trim());
                    } else if (readId == 4) {
                        donateLore.add(line.trim());
                    } else if (readId == 5) {
                        itembuyLore.add(line.trim());
                    } else if (readId == 6) {
                        itemsellLore.add(line.trim());
                    } else if (readId == 7) {
                        itemgetawayLore.add(line.trim());
                    } else if (readId == 8) {
                        moneyrequiredLore.add(line.trim());
                    } else if (readId == 9) {
                        challengeitemLore.add(line.trim());
                    }else if (readId == 10) {
                        returer.add(line.trim());
                    }else if (readId == 11) {
                        infoF.add(line.trim());
                    }else if (readId == 12) {
                        infoL.add(line.trim());
                    }else if (readId == 13) {
                        infoD.add(line.trim());
                    }
                    else if (readId == 100) {
                        refreshLore.add(line.trim());
                    }
                    else {
                        getLogger().warning("Unexpected line in config.yml: " + line);
                    }
                }

                // Обрабатываем описание предметов

            }


        } else if (FileName == "items.yml") {
            File file = new File("plugins/market/" + FileName);
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        // Файл успешно создан
                        // Теперь нужно записать в него нужный текст
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                            writer.write("// https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html\n");
                            writer.write("// Предметы выбирать тут");
                        } catch (IOException e) {
                            getLogger().log(Level.SEVERE, "Failed to write to " + FileName, e);
                        }
                    } else {
                        getLogger().warning("Failed to create " + FileName);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } //Всякие проверки
            int readId = 0;
            int readIndex = 0;
            List<matquest> ItemsForLEVEL = new ArrayList<>();
            List<challenge> ItemsForchal = new ArrayList<>();

            Stream<String> lines = null;
            try {
                lines = Files.lines(Paths.get(file + ""))
                        .map(line -> line.split("\n"))
                        .flatMap(Arrays::stream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (String line : lines.toArray(String[]::new)) {
                if (line.startsWith("//") || line.length() <= 1) {
                    continue;
                }
                else if (line.contains(":") && line.length() <= 3) {
                    readIndex = parseInt(line.substring(0, line.indexOf(":"))) - 1;
                    if (readId == 3) {
                        if (readIndex != 0) {
                            List<matquest> newLevelList = new ArrayList<>(ItemsForLEVEL);
                            ItemsForLVL.add(newLevelList);
                        }
                        ItemsForLEVEL = new ArrayList<>();
                    }
                    if (readId == 6) {
                        if (readIndex != 10) {
                            List<singleton.challenge> newChallengesList = new ArrayList<>(ItemsForchal);
                            ItemsChallenges.add(newChallengesList);
                        }
                        ItemsForchal = new ArrayList<>();
                    }
                }
                else if (line.contains("/STOP")) {
                    break;
                }
                else if (line.contains("schedule(")) {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String formattedTime = now.format(formatter);
                    System.out.println(formattedTime);
                    String datesString = line.substring(line.indexOf("("), line.indexOf(")"));
                    datesArray.addAll(Arrays.asList(datesString.split(";")));
                    System.out.println(datesArray);
                }
                else if (line.contains("refresh")) {
                    refreshPrice = parseInt(line.replace("refresh:",""));
                    continue;
                }
                else if (line.contains("forSale")) {
                    readId = 1;
                    continue;
                } else if (line.contains("forBuy")) {
                    readId = 2;
                    continue;
                } else if (line.contains("forLVL")) {
                    readId = 3;
                    continue;
                } else if (line.contains("MONEYperlevel")) {
                    readId = 4;
                    continue;
                } else if (line.contains("BLOCKPASSperlevel")) {
                    readId = 5;
                    continue;
                } else if (line.contains("QUESTS")) {
                    readId = 6;
                    continue;
                }
                else if (line.contains("forDONATESHOP")) {
                    readId = 7;
                    continue;
                }
                else if (line.contains("GainerBuy")) {
                    readId = 8;
                    continue;
                }
                else if (line.contains("GainerSell")) {
                    readId = 9;
                    continue;
                }
                else if (line.contains("GainerBuyLimit")) {
                    readId = 10;
                    continue;
                }
                else if (line.contains("GainerSellLimit")) {
                    readId = 11;
                    continue;
                }
                else if (line.contains("BLOCKPASSminreq")) {
                    readId = 12;
                    continue;
                }
                String[] parts = line.split(",");
                int counter = 0;
                ItemStack mat = new ItemStack(Material.AIR);
                int i1 = 0;
                double i2 = 0;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                if (readId == 1) {

                    for (String part : parts) {
                        counter++;
                        if (counter == 1) {
                            mat = createItemStack(part);
                        } else if (counter == 2) {
                            i1 = parseInt(part);
                        } else if (counter == 3) {
                            i2 = parseDouble(part);
                        }
                    }

                    ItemsForSale.add(new matlip(mat, i1, i2));
                }
                else if (readId == 2) {
                    for (String part : parts) {
                        counter++;
                        if (counter == 1) {
                            mat = createItemStack(part);
                        } else if (counter == 2) {
                            i1 = parseInt(part);
                        } else if (counter == 3) {
                            i2 = parseDouble(part);
                        }

                    }

                    ItemsForBuy.add(new matlip(mat, i1, i2));
                }
                else if (readId == 3) {
                    for (String part : parts) {
                        counter++;
                        if (counter == 1) {
                            mat = createItemStack(part);

                        } else if (counter == 2) {
                            i1 = parseInt(part);
                        } else if (counter == 3) {
                            i5 = parseInt(part);
                        } else if (counter == 4) {
                            i3 = parseInt(part);
                        } else if (counter == 5) {
                            i4 = parseInt(part);
                        }

                    }
                    ItemsForLEVEL.add(new matquest(mat, i1, i5, i3, i4));
                } else if (readId == 4) {
                    List<String> cmds = new ArrayList<>();
                    parts = line.split(":");
                    for (String part : parts) {
                        counter++;
                        if (counter == 1) {
                            i1 = parseInt(part);
                        } else if (counter == 2) {
                            i2 = parseDouble(part);
                        }
                        else {
                            cmds.add(part);

                        }

                    }
                    MoneyPerLevel[i1 - 1] = i2;
                    CommandsPerLevel.add(cmds);
                    cmds = new ArrayList<>();
                } else if (readId == 5) {
                    parts = line.split(":");
                    for (String part : parts) {
                        counter++;
                        if (counter == 1) {
                            i1 = parseInt(part);
                        } else if (counter == 2) {
                            i2 = parseInt(part);
                        }

                    }
                    BlocksPerLevel[i1 - 1] = i2;
                } else if (readId == 6 && line.length() >= 5) {
                    String[] eventParts = line.trim().split(",");
                    PlayEventQuest.EventType eventType = null;
                    String modificator = eventParts[1];
                    int downA = parseInt(eventParts[2].trim());
                    int topA = parseInt(eventParts[3].trim());
                    int downR = parseInt(eventParts[4].trim());
                    int topR = parseInt(eventParts[5].trim());

                    switch (eventParts[0]) {
                        case "ITEM_DAMAGE":
                            eventType = PlayEventQuest.EventType.ITEM_DAMAGE;
                            break;
                        case "LEVEL_CHANGE":
                            eventType = PlayEventQuest.EventType.LEVEL_CHANGE;
                            break;
                        case "PLAYER_MOVE":
                            eventType = PlayEventQuest.EventType.PLAYER_MOVE;
                            break;
                        case "ITEM_BREAK":
                            eventType = PlayEventQuest.EventType.ITEM_BREAK;
                            break;
                        case "SHEAR_ENTITY":
                            eventType = PlayEventQuest.EventType.SHEAR_ENTITY;
                            break;
                        case "BLOCK_BREAK":

                            System.out.println("Temporarly not worked");
                            break;
                        case "DROP_ITEM":
                            eventType = PlayEventQuest.EventType.DROP_ITEM;
                            break;
                        case "HARVEST_BLOCK":
                            eventType = PlayEventQuest.EventType.HARVEST_BLOCK;
                            break;
                        case "PLAYER_FISH":
                            eventType = PlayEventQuest.EventType.PLAYER_FISH;
                            break;
                        case "EXP_CHANGE":
                            eventType = PlayEventQuest.EventType.EXP_CHANGE;
                            break;
                        case "ITEM_MEND":
                            eventType = PlayEventQuest.EventType.ITEM_MEND;
                            break;
                        case "ADVANCEMENT_DONE":
                            eventType = PlayEventQuest.EventType.ADVANCEMENT_DONE;
                            break;
                        default:
                            System.out.println("Some null quests in items.yml :" + line);
                            break;
                    }
                    ItemsForchal.add(new challenge(eventType, modificator, downA, topA, downR, topR));

                }
                else if (readId == 7) {
                    for (String part : parts) {
                        counter++;
                        if (counter == 1) {
                            mat = createItemStack(part);
                        } else if (counter == 2) {
                            i1 = parseInt(part);
                        } else if (counter == 3) {
                            i2 = parseDouble(part);
                        }

                    }
                    ItemsForDonateShop.add(new matlip(mat, i1, i2));
                }
                else if (readId == 8) { //Buy
                    String[] Parts = line.trim().split(":");

                    Gainer[0][parseInt(Parts[0])-1] = parseDouble(Parts[1]);
                }
                else if (readId == 9) { //Sell
                    String[] Parts = line.trim().split(":");
                    Gainer[1][parseInt(Parts[0])-1] = parseDouble(Parts[1]);
                }
                else if (readId == 10) { //Buy
                    String[] Parts = line.trim().split(":");
                    Gainer[2][parseInt(Parts[0])-1] = parseDouble(Parts[1]);
                }
                else if (readId == 11) { //Sell
                    String[] Parts = line.trim().split(":");
                    Gainer[3][parseInt(Parts[0])-1] = parseDouble(Parts[1]);
                }
                else if (readId == 12) { //BMR
                    parts = line.split(":");
                    for (String part : parts) {
                        counter++;
                        if (counter == 1) {
                            i1 = parseInt(part);
                        } else if (counter == 2) {
                            i2 = parseInt(part);
                        }

                    }
                    BlocksMinReq[i1 - 1] = i2;
                }
            }
            //ItemsForLVL.add(ItemsForLEVEL); //Так как последний элемент никак не прочитается
            ItemsChallenges.add(ItemsForchal); //Так как последний элемент никак не прочитается
        } else if (FileName == "messages.yml") {
            File file = new File("plugins/market/" + FileName);
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        // Файл успешно создан
                        // Теперь нужно записать в него нужный текст
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                            writer.write(""); //After Release
                        } catch (IOException e) {
                            getLogger().log(Level.SEVERE, "Failed to write to " + FileName, e);
                        }
                    } else {
                        getLogger().warning("Failed to create " + FileName);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } //Если файл не создан

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String key = parts[0];
                        String value = parts[1];
                        if (key.endsWith("_NULL")) {
                            // Если ключ заканчивается на _NULL, то значение добавляется в карту ExplainNull
                            key = key.replace("_NULL", "");
                            ExplainNull.put(key, value);
                        } else {
                            // Иначе значение добавляется в карту Explain
                            Explain.put(key, value);
                        }
                        if (key.equals("limit=0") || key.equals("items=0") || key.equals("items=0FS") || key.equals("items<64SS") ||
                                key.equals("limit=0GA") || key.equals("limit=0OTIGA") || key.equals("items=0GA") || key.equals("items=0FSGA") ||
                                key.equals("items<64SSGA") || key.equals("RewardRecievedGA") || key.equals("TradesUPD") || key.equals("LVLREQ")||
                                key.equals("NEMoney") || key.equals("NEKubaks") || key.equals("MaxLR")) {
                            // Если ключ соответствует одному из перечисленных, то значение добавляется в список Messages
                            Messgaes.add(value);
                        }
                    }
                }
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Failed to read " + FileName, e);
            }
        } else if (FileName == "translations.yml") {
            File file = new File("plugins/market/" + FileName);
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        // Файл успешно создан
                        // Теперь нужно записать в него нужный текст
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                            writer.write(""); //After Release
                        } catch (IOException e) {
                            getLogger().log(Level.SEVERE, "Failed to write to " + FileName, e);
                        }
                    } else {
                        getLogger().warning("Failed to create " + FileName);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } //Если файл не создан
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        if(!(parts[0].equalsIgnoreCase("DEFAULTCOLOR"))) {
                            Translations.put(parts[0], parts[1]);
                        }
                        else {
                            DEFCOLOR = parts[1];
                        }

                    }
                }
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Failed to read " + FileName, e);
            }

        }
        //return ret;

    }


    public static class matlip implements Serializable {
        private Fartsovschikkubogram.SerializableItemStack material;
        private double price;
        private int limit;

        public matlip(ItemStack material, int limit, double price) {
            this.material = new Fartsovschikkubogram.SerializableItemStack(material);
            this.price = price;
            this.limit = limit;
        }

        public ItemStack getMaterial() {
            return material.getItemStack();
        }

        public double getPrice() {
            return price;
        }

        public int getLimit() {
            return limit;
        }

        public void SubstractLimit(int sub) {
            limit -= sub;
        }
    }

    public static class matquest {
        private ItemStack material;
        private int Da;
        private int Ta;
        private int Dr;
        private int Tr;

        public matquest(ItemStack material, int Da, int Ta, int Dr, int Tr) {
            this.material = material;
            this.Da = Da;
            this.Ta = Ta;
            this.Dr = Dr;
            this.Tr = Tr;
        }

        public ItemStack getMaterial() {
            return material;
        }

        public int GetRandomAmount() {
            Random random = new Random();
            int randomAmount = random.nextInt(Ta - Da + 1) + Da;
            randomAmount = (randomAmount / 10) * 10; // округляем до ближайшего числа, кратного 10
            return randomAmount;
        }

        public int GetRandomReward() {
            Random random = new Random();
            int randomAmount = random.nextInt(Tr - Dr + 1) + Dr;
            randomAmount = (randomAmount / 50) * 50; // округляем до ближайшего числа, кратного 10
            return randomAmount;
        }
    }

    public static class challenge implements Serializable {

        private PlayEventQuest.EventType event;
        private String modifiactor;
        private int Amount;
        private int Reward;

        public challenge(PlayEventQuest.EventType event, String modificator, int downA, int topA, int downR, int topR) {
            Random random = new Random();
            this.event = event;
            this.modifiactor = modificator;
            this.Amount = ((random.nextInt(topA - downA + 1) + downA) / 2) * 2;
            this.Reward = ((random.nextInt(topR - downR + 1) + downR) / 100) * 100;
        }

        public PlayEventQuest.EventType RetEvent() {
            return event;
        }

        public String RetModify() {
            return modifiactor;
        }

        public int RetAmount() {
            return Amount;
        }

        public void Substract(int sub) {
            Amount -= sub;
        }

        int RetReward() {
            return Reward;
        }

        void GetAndDeleteReward(Player p) {
            double dob = Reward;
            CMI.getInstance().getPlayerManager().getUser(p).deposit(dob);
            Reward = 0;
        }


    }

    public static class ssound {

        private float pitch;
        private Sound sound;

        public ssound(float pitch, Sound sound) {
            this.pitch = pitch;
            this.sound = sound;
        }

        void PlaySound(Player player) {
            Location location = player.getLocation();
            player.playSound(location, sound, 1.0f, pitch);
        }

    }



    public static ItemStack ZeroAmountIS(ItemStack origin) {
        ItemStack itemDrop = new ItemStack(origin.getType(), 1);
        ItemMeta meta = origin.getItemMeta();
        if (meta instanceof EnchantmentStorageMeta) {
            EnchantmentStorageMeta enchMeta = (EnchantmentStorageMeta) meta;
            for (Map.Entry<Enchantment, Integer> entry : origin.getEnchantments().entrySet()) {
                enchMeta.addStoredEnchant(entry.getKey(), entry.getValue(), true);
            }
            itemDrop.setItemMeta(enchMeta);
        }
        return itemDrop;
    }

    public static List<String> replaceVariables(List<String> list, Map<String, String> values) {
        List<String> result = new ArrayList<>();
        for (String line : list) {
            for (Map.Entry<String, String> entry : values.entrySet()) {
                line = line.replace("%" + entry.getKey() + "%", entry.getValue());
            }
            result.add(singleton.processString(line));
        }
        return result;
    }

    public List<String> removeFirstElement(List<String> originalList) {
        List<String> newList = new ArrayList<>(originalList);
        if (!newList.isEmpty()) {
            newList.remove(0);
        }
        return newList;
    }
}
